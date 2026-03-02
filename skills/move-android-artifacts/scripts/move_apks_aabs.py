#!/usr/bin/env python3
import argparse
import os
import shutil
from pathlib import Path

EXTS = {".apk", ".aab", ".apks"}

def iter_artifacts(root: Path):
    for p in root.rglob("*"):
        if p.is_file() and p.suffix.lower() in EXTS:
            yield p

def unique_dest_path(dest_dir: Path, filename: str) -> Path:
    candidate = dest_dir / filename
    if not candidate.exists():
        return candidate
    stem = Path(filename).stem
    suffix = Path(filename).suffix
    i = 1
    while True:
        candidate = dest_dir / f"{stem} ({i}){suffix}"
        if not candidate.exists():
            return candidate
        i += 1

def main():
    ap = argparse.ArgumentParser(description="Move .apk/.aab/.apks files into ~/Desktop/apks")
    ap.add_argument("--dest", required=True, help="Destination directory, e.g. ~/Desktop/apks")
    ap.add_argument("--sources", nargs="+", required=True, help="One or more source directories")
    ap.add_argument("--dry-run", action="store_true", help="List actions without moving files")
    args = ap.parse_args()

    dest_dir = Path(os.path.expanduser(args.dest)).resolve()
    sources = [Path(os.path.expanduser(s)).resolve() for s in args.sources]

    # Validate sources
    bad = [str(s) for s in sources if not s.exists() or not s.is_dir()]
    if bad:
        raise SystemExit(f"These sources are not valid directories:\n- " + "\n- ".join(bad))

    found = []
    for s in sources:
        found.extend(list(iter_artifacts(s)))

    # Ensure dest exists (even for dry-run we can show the intended path)
    if not args.dry_run:
        dest_dir.mkdir(parents=True, exist_ok=True)

    moved = 0
    planned = []

    for src in found:
        target = unique_dest_path(dest_dir, src.name)
        planned.append((src, target))

    print(f"Destination: {dest_dir}")
    print(f"Sources: " + ", ".join(str(s) for s in sources))
    print(f"Found artifacts: {len(found)}")

    for src, target in planned:
        if args.dry_run:
            print(f"[DRY-RUN] Would move: {src} -> {target}")
        else:
            # If dest didn't exist and we're moving, ensure it's there
            dest_dir.mkdir(parents=True, exist_ok=True)
            shutil.move(str(src), str(target))
            moved += 1
            print(f"Moved: {src} -> {target}")

    if args.dry_run:
        print("Dry-run complete. Re-run without --dry-run to execute.")
    else:
        print(f"Done. Moved {moved} files into {dest_dir}")

if __name__ == "__main__":
    main()
