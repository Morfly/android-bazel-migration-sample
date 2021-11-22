   # Android Bazel Migration Sample
   
   Sample application for demonstrating the migration approaches of Android Gradle application to Bazel.
   
   This repository consists of **3 branches**:
   - **automated_migration**: contains a simple Gradle plugin that allows to automatically migrate Gradle
   project to Bazel using.
   - **manual_migration_1**: shows a manual migration to Bazel where each `BUILD.bazel` file corresponds to `build.gradle`.
   - **manual_migration_granular**: That adds more build granularity to the `app` directory by adding more `BUILD.bazel`
   files and targets per 1 Gradle module.
   
   In order to start auto-migration run:
   ```shell
   ./gradlew migrateToBazel
   ```
   
   In order to build Bazel project run:
   ```shell
   bazel build //app:bin
   ```
   
   In order to install and launch Bazel project to the mobile device run:
   ```shell
   bazel mobile-install //app:bin --start_app
   ```
