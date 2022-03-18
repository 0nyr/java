# Quizz

#### Which is the correct return type for the processFunction method?

```java
_____ processFunction(Integer number, Function<Integer, String> lambda) {
        return lambda.apply(number);
    }
```

- [ ] `Integer`
- [X] `String`
- [ ] `Consumer`
- [ ] `Function<Integer, String>`

Compiling and executing correct code:

```shell
odd(base) onyr@aezyr:~/Documents/code/java/training/linkedin_assessment/which_return_type_0$ javac Main.java
(base) onyr@aezyr:~/Documents/code/java/training/linkedin_assessment/which_return_type_0$ java Main 
odd
```
