import os
import logging

# /Users/zhangchaozhou/Library/Android/sdk/system-images
# /Users/zhangchaozhou/Library/Caches
# /Users/zhangchaozhou/.gradle/caches


TOP_N = 10
ROOT_DIR = "src"

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s"
)

def format_size(size):
    for unit in ['B', 'KB', 'MB', 'GB', 'TB']:
        if size < 1024:
            return f"{size:.2f}{unit}"
        size /= 1024
    return f"{size:.2f}PB"


def scan_files(root_dir):
    zip_files = []
    mapping_files = []

    for root, dirs, files in os.walk(root_dir):
        for file in files:

            path = os.path.join(root, file)

            if file.lower().endswith(".zip"):
                try:
                    size = os.path.getsize(path)
                    zip_files.append((path, size))
                except Exception as e:
                    logging.error(f"Error reading {path}: {e}")

            if file == "mapping.txt":
                mapping_files.append(path)

    # 排序 zip
    zip_files.sort(key=lambda x: x[1], reverse=True)
    zip_targets = zip_files[:TOP_N]

    logging.info("======= TOP %d ZIP FILES (TO DELETE) =======", TOP_N)
    for path, size in zip_targets:
        logging.info(f"{format_size(size):>10} | {path}")
    logging.info("============================================")

    logging.info("======= mapping.txt FILES (TO DELETE) =======")
    for path in mapping_files:
        logging.info(path)
    logging.info("============================================")


    # 删除 mapping.txt
    for path in mapping_files:
        try:
            os.remove(path)
            logging.info(f"Deleted mapping.txt: {path}")
        except Exception as e:
            logging.error(f"Failed to delete {path}: {e}")


    confirm = input("Delete these files? (y/N): ")
    if confirm.lower() != "y":
        logging.info("Deletion cancelled")
        return

    # 删除 zip
    for path, _ in zip_targets:
        try:
            os.remove(path)
            logging.info(f"Deleted zip: {path}")
        except Exception as e:
            logging.error(f"Failed to delete {path}: {e}")





if __name__ == "__main__":
    src_dir = "/Users/zhangchaozhou/Library/Containers/com.tencent.WeWorkMac/Data/Documents/Profiles/D511B93AC2D24928F61476927A9D8AAA/Caches/Files"  # 需要扫描的目录
    scan_files(src_dir)