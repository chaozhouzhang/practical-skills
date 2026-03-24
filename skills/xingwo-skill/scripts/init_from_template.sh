#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 || $# -gt 3 ]]; then
  echo "Usage: $0 <target_dir> [app_name] [package_name]" >&2
  exit 1
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SKILL_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
TEMPLATE_DIR="${SKILL_DIR}/assets/project-template"

TARGET_DIR="$1"
APP_NAME="${2:-}"
PACKAGE_NAME="${3:-}"

if [[ ! -d "${TEMPLATE_DIR}" ]]; then
  echo "Template not found: ${TEMPLATE_DIR}" >&2
  exit 1
fi

mkdir -p "${TARGET_DIR}"

rsync -a --delete \
  --exclude '.git/' \
  --exclude '.gradle/' \
  --exclude '.kotlin/' \
  --exclude '.idea/' \
  --exclude 'build/' \
  --exclude 'app/build/' \
  --exclude 'local.properties' \
  "${TEMPLATE_DIR}/" "${TARGET_DIR}/"

DEFAULT_PACKAGE="com.example.xinqingwu"
DEFAULT_APP_NAME="心晴屋"
DEFAULT_PROJECT_NAME="XinQingWuPrototype"

replace_in_file() {
  local file="$1"
  local from="$2"
  local to="$3"
  perl -0pi -e "s/\Q${from}\E/${to//\//\\/}/g" "${file}"
}

if [[ -n "${APP_NAME}" ]]; then
  if [[ -f "${TARGET_DIR}/app/src/main/res/values/strings.xml" ]]; then
    replace_in_file "${TARGET_DIR}/app/src/main/res/values/strings.xml" "${DEFAULT_APP_NAME}" "${APP_NAME}"
  fi
  if [[ -f "${TARGET_DIR}/settings.gradle.kts" ]]; then
    replace_in_file "${TARGET_DIR}/settings.gradle.kts" "rootProject.name = \"${DEFAULT_PROJECT_NAME}\"" "rootProject.name = \"${APP_NAME}\""
  fi
fi

if [[ -n "${PACKAGE_NAME}" && "${PACKAGE_NAME}" != "${DEFAULT_PACKAGE}" ]]; then
  while IFS= read -r -d '' file; do
    replace_in_file "${file}" "${DEFAULT_PACKAGE}" "${PACKAGE_NAME}"
  done < <(find "${TARGET_DIR}" -type f \( -name "*.kt" -o -name "*.kts" -o -name "*.xml" \) -print0)

  OLD_PACKAGE_PATH="$(printf '%s' "${DEFAULT_PACKAGE}" | tr '.' '/')"
  NEW_PACKAGE_PATH="$(printf '%s' "${PACKAGE_NAME}" | tr '.' '/')"
  OLD_PATH="${TARGET_DIR}/app/src/main/java/${OLD_PACKAGE_PATH}"
  NEW_PATH="${TARGET_DIR}/app/src/main/java/${NEW_PACKAGE_PATH}"
  if [[ -d "${OLD_PATH}" ]]; then
    mkdir -p "$(dirname "${NEW_PATH}")"
    rm -rf "${NEW_PATH}"
    mv "${OLD_PATH}" "${NEW_PATH}"
    find "${TARGET_DIR}/app/src/main/java" -type d -empty -delete
  fi
fi

echo "Project initialized at: ${TARGET_DIR}"
echo "App name: ${APP_NAME:-${DEFAULT_APP_NAME}}"
echo "Package: ${PACKAGE_NAME:-${DEFAULT_PACKAGE}}"
