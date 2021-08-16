## Configuración Gitlab-Sonarqube
En la plantilla se agrega el archivo `.gitlab-ci.yml`,
para realizar la automatizacion de escaneos de codigo hacia sonarqube.

### Archivo `.gitlab-ci.yml`

```terminal
## YAML Template.
---
stages:
  - sonarQube
  sonar:
  image: harbor.coppel.io/library/sonar-scanner:3.2.0
    stage: sonarQube
    tags:
    - docker
  script:
    - export NODE_PATH=$NODE_PATH:`npm root -g`
    - sonar-scanner -Dsonar.host.url=$CPL_SONARQUBE_DEV -Dsonar.projectKey=$CPL_PROJECT_KEY -Dsonar.projectName=$CI_PROJECT_NAME -Dsonar.projectVersion=$CPL_PROJECT_VERSION

    only:
      refs:
        - merge_requests
      variables:
        - $CI_MERGE_REQUEST_TARGET_BRANCH_NAME == "master"
        - $CI_MERGE_REQUEST_TARGET_BRANCH_NAME =~ /\d{1,6}_\d{6}_(\w*Desarrollo\w*)/
  except:
    - branches
  ```
En este archivo se especifica el stage de sonarqube el cual sera detonado cuando exista un merge request hacia la rama de `Desarrollo`o rama `master`, en este archivo se hacen referencia a unas variables que se necesitan declarar en el repositorio:
Para ello hay que entrar en la seccion **SETTINGS/CI-CD** del repositorio correspondiente y expandir la seccion de variables, en esta seccion hacer click sobre (add variable / agregar variable).
(más info en developers).

**$CPL_PROJECT_KEY** Key único compuesto por el número de centro : nombre del proyecto ej. 230451:PlantillaWPF

**$CPL_PROJECT_VERSION** Número de versión del proyecto ej. 1.0.0

**$CPL_SONARQUBE_** Esta constante tiene el valor de las distintas instancias de sonar que existe (desarrollo, afore, sudamerica). Los cuales pueden ser modificado en el YML de acuerdo a su division.

Siendo los siguientes: `$CPL_SONARQUBE_DEV`, `$CPL_SONARQUBE_AFORE`, `$CPL_SONARQUBE_SUDAMERICA`.
