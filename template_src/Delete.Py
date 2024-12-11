import os 
import shutil
import subprocess

def delete_files_and_folders(target_dir, folders_to_delete, files_to_delete, cleanNPM = False):
    if cleanNPM:
        subprocess.run("npm cache clean --force ", shell=True, check=True)

    for root, dirs, files in os.walk(target_dir, topdown=False):
        # Delete specified files
        for file_name in files:
            if file_name in files_to_delete:
                file_path = os.path.join(root, file_name)
                try:
                    os.remove(file_path)
                    print(f"Deleted file: {file_path}")
                except Exception as e:
                    print(f"Failed to delete file: {file_path}. Error: {e}")

        # Delete specified folders
        for dir_name in dirs:
            if dir_name in folders_to_delete:
                folder_path = os.path.join(root, dir_name)
                try:
                    shutil.rmtree(folder_path)
                    print(f"Deleted folder: {folder_path}")
                except Exception as e:
                    print(f"Failed to delete folder: {folder_path}. Error: {e}")

if __name__ == "__main__":
    # Define the root directory of your project
    project_dir = "ExampleError//template_src"

    # List of folder names to delete
    folders = ["platforms","node_modules","plugins"]

    # List of file names to delete
    files = ["package-lock.json",]

    # Call the function
    delete_files_and_folders(project_dir, folders, files, True)
