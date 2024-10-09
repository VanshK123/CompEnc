# File Upload, Compression, and Encryption API

This project provides a RESTful API built with Spring Boot that allows users to upload files. The files are processed in three steps:
1. **Compression**: The uploaded file is compressed using the Snappy compression algorithm.
2. **Encryption**: The compressed file is then encrypted using AES-128 encryption.
3. **File Saving**: The encrypted file is saved to the local file system under an `uploads/` directory.

After each operation (compression, encryption, and saving), the API responds with the time taken for each process and the total time for all operations.

## Prerequisites

- Java 8 or higher
- Maven (for building the project)
- curl (for testing the API)

## Project Setup

1. **Clone the repository**:

   ```bash
   git clone <repository-url>
   cd <repository-directory>
2. **Build the project using Maven**:

   Ensure you are in the root directory of the project, then run:

   ```bash
   mvn clean install
3. **Run the Spring Boot Application**:

   You can run the application using the following command:

   ```bash
   mvn spring-boot:run
    
    This will start the application and run it on `localhost:8080` by default.

## How to Use the API

Once the application is running, you can upload a file using the following `curl` command:

```bash
curl.exe -X POST -F "file=@/path/to/your/file.pdf" http://localhost:8080/api/files/upload

Replace `/path/to/your/file.pdf` with the actual path to the file you want to upload.

### Steps to Use the API

1. **Upload a File**:

   Run the `curl` command to upload your file to the API. The file can be of any type, and the API will compress, encrypt, and save the file. The API will return timing information for each operation (compression, encryption, and saving) along with the total processing time.

2. **Example Response**:

   Upon successful file upload and processing, the API will return a response like this:

   ```bash
   File uploaded, compressed, and encrypted successfully.
   Compression Time: 120 ms
   Encryption Time: 50 ms
   Saving Time: 75 ms
   Total Processing Time: 245 ms
3. **Locate the Encrypted File**:

   After the file has been successfully uploaded, compressed, and encrypted, the resulting file will be saved in the `uploads/` directory in the project’s root folder. The file will have a `.enc` extension, indicating that it has been encrypted.

   Example:


4. **Handling Errors**:

If there is an issue during file upload or processing, the API will respond with a `500 Internal Server Error` and provide an error message detailing what went wrong.

Example error response:

```bash
Failed to upload file: [Error message]
  
   You can check the error message and review the server logs to investigate the issue further.

## Directory Structure

The directory structure of the project, including where the uploaded and processed files will be saved, is as follows:

- **src/**: Contains the source code for the application.
- **target/**: Contains the compiled output files after building the project.
- **uploads/**: This is the directory where the encrypted files are saved after they are uploaded and processed by the API.
- **pom.xml**: Maven configuration file for the project.

## Important Notes

- **Encryption Key**: The AES-128 encryption uses a predefined key (`aesEncryptionKey`). This key is hardcoded for simplicity but should be securely managed in a production environment. You may consider using environment variables or a secure key management service for handling encryption keys in production.
- **Uploads Directory**: Ensure the application has proper write access to the `uploads/` directory. If the directory does not exist, it will be created automatically during the first file upload.

## Testing the API

To test the API, use the `curl` command as provided earlier. Replace the file path in the command with the path to the file you want to upload. For example:

```bash
curl.exe -X POST -F "file=@/Users/username/Documents/testfile.pdf" http://localhost:8080/api/files/upload


