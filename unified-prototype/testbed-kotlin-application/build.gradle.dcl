kotlinApplication {
    dependencies {
        implementation(project(":kotlin-util"))
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    }

    targets {
        jvm {
            jdkVersion = 17
            mainClass = "com.example.AppKt"
            dependencies {
                implementation("org.apache.commons:commons-lang3:3.14.0")
            }
        }
        nodeJs {
            dependencies {
                implementation("com.squareup.sqldelight:runtime:1.5.5")
            }
        }
        macOsArm64 {
            entryPoint = "com.example.main"
        }
    }
}
