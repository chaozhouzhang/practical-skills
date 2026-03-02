#!/usr/bin/env python3
import argparse
import shutil
import tarfile
from pathlib import Path
import sys


def is_hidden(p: Path):
    return p.name.startswith('.')


def delete_path(path: Path):
    if not path.exists():
        return
    if path.is_file() or path.is_symlink():
        path.unlink()
    else:
        shutil.rmtree(path)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('Source')
    parser.add_argument('Target')
    parser.add_argument('rename')
    parser.add_argument('channel')
    args = parser.parse_args()

    src = Path(args.Source).resolve()
    dest_parent = Path(args.Target).resolve()
    new_name = args.rename
    keep_channel = args.channel

    if not src.is_dir():
        print('ERROR: Source not found')
        sys.exit(1)

    if dest_parent == src or dest_parent in src.parents:
        print('ERROR: Target cannot be inside Source')
        sys.exit(1)

    dest_parent.mkdir(parents=True, exist_ok=True)
    dest = dest_parent / new_name

    if dest.exists():
        print('ERROR: destination already exists')
        sys.exit(1)

    print('MODE: APPLY')
    print(f'COPY: {src} -> {dest}')
    shutil.copytree(src, dest, symlinks=True)

    # Clean app/build
    build_dir = dest / 'app' / 'build'
    if build_dir.exists():
        for child in build_dir.iterdir():
            delete_path(child)

    # Remove top-level hidden files
    for item in dest.iterdir():
        if is_hidden(item):
            delete_path(item)

    # Remove non-target channel dirs
    main_dir = dest / 'app' / 'src' / 'main'
    if main_dir.exists():
        for item in main_dir.iterdir():
            if not item.is_dir():
                continue
            name = item.name
            if name.startswith('assets-'):
                if name not in (f'assets-{keep_channel}', f'assets-{keep_channel}-version'):
                    delete_path(item)
            if name.startswith('res-'):
                if name not in (f'res-{keep_channel}', f'res-{keep_channel}-version'):
                    delete_path(item)

    archive_path = dest_parent / f'{new_name}.tar.gz'
    print(f'ARCHIVE: {archive_path}')
    with tarfile.open(archive_path, 'w:gz') as tar:
        tar.add(dest, arcname=new_name)

    print('DONE')


if __name__ == '__main__':
    main()
