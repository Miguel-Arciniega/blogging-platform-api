# Run the Project: Prerequisites and Instructions

## Prerequisites
Ensure the following tools/software are installed and configured:
1. **JDK 17+**
    - Install JDK version 17 or later.
    - Set the `JAVA_HOME` environment variable to the JDK installation directory.
2. **Gradle 7+**
    - Install Gradle (version 7 or later) and verify it is accessible:
      ```sh
      gradle --version
      ```
3. **Docker**
    - Install Docker and confirm it is running:
      ```sh
      docker --version
      ```

---

## Steps to Run the Project
1. **Clone the Repository:**
   ```sh
   git clone <repository-url>
   cd <project-folder>
   ```

2. **Build and Start the Application:**  
   Execute the following command to clean and run the application:
   ```sh
   ./gradlew clean bootRun
   ```

   **What this does:**
    - Builds the application.
    - Automatically initializes and configures a MySQL database container using Docker.

3. **Access the Application:**  
   Once running, the application will be available at:
   ```
   http://localhost:8080/blogging-platform
   ```
   
---

## Additional Notes
- No manual Docker commands are required. Ensure the Docker daemon is active.
- Local Database is only created in dev environment
- If issues arise:
    - Confirm Docker is correctly set up.
    - Refer to the project's `README.md` or other documentation for troubleshooting.

---