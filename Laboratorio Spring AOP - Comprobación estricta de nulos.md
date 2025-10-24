# Laboratorio Spring AOP: Comprobación estricta de nulos

## Objetivo del laboratorio

Crear un aspecto con Spring  AOP que lance excepciones cuando:

- un método reciba un argumento con valor null (IllegalArgumentException)
- un método devuelva null (NoSuchElementException).

Este laboratorio te guiará paso a paso para crear, integrar y probar el aspecto `StrictNullChecksAspect` en una aplicación Spring Boot. Aprenderás a usar AOP (Programación Orientada a Aspectos) para validar argumentos y retornos nulos en los métodos de tu aplicación.

### Requisitos previos

- Java 17+ (o compatible con tu versión de Spring)
- Maven o Gradle
- IDE (Eclipse, IntelliJ, VS Code)

## Paso 1. Crear el Proyecto Spring Boot

### Usando Spring Initializr

1. Abre [start.spring.io](https://start.spring.io/).
2. Configura:
   - Project: Maven Project
   - Language: Java
   - Spring Boot: 3.3.x o superior
   - Group: `com.example`
   - Artifact: `spring-aop-lab`
   - Name: `spring-aop-lab`
   - Package name: `com.example`
3. Añade las dependencias:
   - Spring Boot DevTools (spring-boot-devtools)
4. Descarga, descomprime e importa el proyecto.

### Editar el fichero pom.xml

Agregar la dependencia:

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

### Crear paquetes

- com.example.aop
- com.example.service

## Paso 2: Clase principal

`src/main/java/com/example/SpringIocLabApplication.java`

```java
package com.example.springaoplab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy // Habilitar anotaciones AspectJ
@SpringBootApplication
public class SpringAopLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringIocLabApplication.class, args);
    }
}
```

## Paso 3. Crear una clase de servicio para probar el aspecto

Crea una clase ejemplo en `src/main/java/com/example/service/SampleService.java`:

```java
package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class SampleService {
    public String echo(String input) {
        return input;
    }
    public String alwaysNull() {
        return null;
    }
}
```

## Paso 4. Crear las Pruebas Automatizadas

Crea un test de integración en `src/test/java/com/example/aop/StrictNullChecksAspectTest.java`:

```java
package com.example.aop;

import com.example.service.SampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StrictNullChecksAspectTest {
    @Autowired
    SampleService service;

    @Test
    void testNullArgumentThrows() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.echo(null));
        assertTrue(ex.getMessage().contains("Illegal argument"));
    }

    @Test
    void testNullReturnThrows() {
        Exception ex = assertThrows(java.util.NoSuchElementException.class, () -> service.alwaysNull());
        assertTrue(ex.getMessage().contains("Returns null"));
    }

    @Test
    void testValidArgumentAndReturn() {
        assertEquals("hello", service.echo("hello"));
    }
}
```

Observaciones:

- Son Test de integración con @SpringBootTest para SampleService sea inyectado y se verifique que se lanzan las excepciones esperadas.
- Importante: las pruebas llaman al bean inyectado (pasa por el proxy y activa el aspecto).
- Los test se pueden ejecutar con `mvn test`o con el propio IDE.

## Paso 5. Ejecuta las pruebas

- testNullArgumentThrows: `falla`
- testNullReturnThrows: `falla`
- testValidArgumentAndReturn: `pasa`

## Paso 6. Crear el aspecto StrictNullChecks

Agrega la clase `StrictNullChecksAspect` con el siguiente código:

`src/main/java/com/example/aop/StrictNullChecksAspect.java` 

```java
package com.example.aop;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class StrictNullChecksAspect {

}
```

## Paso 7. Añadir el consejo (advice) que compruebe los argumentos nulos

`src/main/java/com/example/aop/StrictNullChecksAspect.java` 

```java
package com.example.aop;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class StrictNullChecksAspect {
    @Before("execution(public * com.example..*.*(*,..))")
    public void nullArgument(JoinPoint jp) {
        for(var i = 0; i < jp.getArgs().length; i++) {
            if(Objects.isNull(jp.getArgs()[i])) 
                throw new IllegalArgumentException(String.format("Illegal argument %d in method '%s'", i + 1, jp.getSignature()));
        }
    }
}
```

## Paso 8. Ejecuta las pruebas

- testNullArgumentThrows: `pasa`
- testNullReturnThrows: `falla`
- testValidArgumentAndReturn: `pasa`

## Paso 9. Añadir el consejo (advice) que compruebe valores nulos de retorno

`src/main/java/com/example/aop/StrictNullChecksAspect.java` 

```java
package com.example.aop;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class StrictNullChecksAspect {
    @Before("execution(public * com.example..*.*(*,..))")
    public void nullArgument(JoinPoint jp) {
        for(var i = 0; i < jp.getArgs().length; i++) {
            if(Objects.isNull(jp.getArgs()[i])) 
                throw new IllegalArgumentException(String.format("Illegal argument %d in method '%s'", i + 1, jp.getSignature()));
        }
    }
    @AfterReturning(pointcut="execution(* com.example..*.*(..)) && !execution(void *(..))", returning="retVal")
    public void nullReturn(JoinPoint jp, Object retVal) {
        if(Objects.isNull(retVal))
            throw new NoSuchElementException(String.format("Returns null in method '%s'", jp.getSignature()));
    }
}
```

## Paso 10. Ejecuta las pruebas

- testNullArgumentThrows: `pasa`
- testNullReturnThrows: `pasa`
- testValidArgumentAndReturn: `pasa`

## Explicación de Funcionamiento

- **@Aspect:** Marca la clase como aspecto de AOP.
- **@Before:** Intercepta antes de ejecutar métodos públicos en `com.example..*` con al menos un parámetro y lanza excepción si algún argumento es nulo.
- **@AfterReturning:** Intercepta después de métodos con retorno (no `void`) y lanza excepción si el retorno es nulo.

## Estructura base

    spring-ioc-lab/
    ├─ src/main/java/com/example/
    │   ├─ SpringAopLabApplication.java
    │   ├─ aop/StrictNullChecksAspect.java
    │   └─ service/SampleService/
    ├─ src/main/resources/
    │   └─ application.properties
    └─ src/test/java/com/example/
        └─ aop/StrictNullChecksAspectTest.java

## Buenas Prácticas y Extensiones

- Puedes ajustar los pointcuts para afinar los métodos mas problemáticos.
- Considera usar anotaciones personalizadas para marcar métodos a validar en lugar de interceptar todo el paquete
- Agrega logging para una mejor trazabilidad.

## Recursos Adicionales

- [Spring AOP Reference](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [AspectJ Pointcut Expressions](https://www.eclipse.org/aspectj/doc/next/progguide/semantics-pointcuts.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

---
©️JMA 2024