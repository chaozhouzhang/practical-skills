#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
INIT_SCRIPT="${SCRIPT_DIR}/init_from_template.sh"

DEFAULT_TARGET_DIR="${HOME}/Documents/Github/xinqingwu-prototype"
DEFAULT_APP_NAME="心晴屋"
DEFAULT_PACKAGE_NAME="com.example.xinqingwu"

prompt_with_default() {
  local label="$1"
  local default_value="$2"
  local input
  read -r -p "${label} [${default_value}]: " input
  if [[ -z "${input}" ]]; then
    printf '%s\n' "${default_value}"
  else
    printf '%s\n' "${input}"
  fi
}

if [[ ! -x "${INIT_SCRIPT}" ]]; then
  echo "Init script not found or not executable: ${INIT_SCRIPT}" >&2
  exit 1
fi

TARGET_DIR="$(prompt_with_default "Target directory" "${DEFAULT_TARGET_DIR}")"
APP_NAME="$(prompt_with_default "App name" "${DEFAULT_APP_NAME}")"
PACKAGE_NAME="$(prompt_with_default "Package name" "${DEFAULT_PACKAGE_NAME}")"

bash "${INIT_SCRIPT}" "${TARGET_DIR}" "${APP_NAME}" "${PACKAGE_NAME}"
