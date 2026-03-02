---
name: delete-useless-channel
description: >
  Optimize Android multi-channel source code by keeping only the specified channel,
  removing unused channel resources and build artifacts, then generating a compressed archive.
version: 1.0.3
author: zhangchaozhou
entry: delete_useless_channel.py
runtime: python3
default_mode: apply
---

# delete-useless-channel

## Purpose

Before Android multi-channel builds:

- Keep only the specified channel resources
- Remove unused channel directories
- Clean build artifacts
- Remove top-level hidden files
- Generate compressed archive

## Inputs

Source  - Source project root directory  
Target  - Destination parent directory  
rename  - New directory name  
channel    - Target channel name  

## Execution

Apply mode (default):

    python3 delete_useless_channel.py Source Target rename channel

## Behavior

1. Copy Source → Target/rename
2. Remove:
   - rename/app/build/*
   - Top-level hidden files and directories (.*)
   - app/src/main/assets-* (except assets-channel & assets-channel-version)
   - app/src/main/res-* (except res-channel & res-channel-version)
3. Generate Target/rename.zip

## Safety

- Never modifies source directory
- Refuses to overwrite existing destination
- Refuses if Target is inside Source
