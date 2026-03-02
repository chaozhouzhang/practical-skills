---
name: move-android-artifacts
description: |
  Use this skill when the user wants to collect Android build artifacts (.apk/.aab/.apks)
  from one or more local folders on macOS, and move them into ~/Desktop/apks,
  creating the folder if it does not exist. Avoid overwriting on name conflicts.
---

## Goal
Move *.apk and *.aab nad *apks files from user-specified directories into ~/Desktop/apks on macOS.

## Inputs to ask/confirm (if missing)
- A list of source directories (absolute paths preferred).
- Whether to do a dry-run first.

## Steps
1) Run the helper script:
   python3 scripts/move_apks_aabs.py --dest ~/Desktop/apks --sources <sources...>
2) Default to --dry-run first unless the user explicitly says to move now.
3) Show a summary: how many found, moved, skipped, and the destination path.