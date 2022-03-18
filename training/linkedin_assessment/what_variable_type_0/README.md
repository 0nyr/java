# Quizz

#### What variable type should be declared for capitalize?

```shell
List<String> songTitles = Arrays.asList("humble", "element", "dna");
_______ capitalize = (str) -> str.toUpperCase();
songTitles.stream().map(capitalize).forEach(System.out::println);
```

- [X] `Function<String, String>`
- [ ] `Stream<String>`
- [ ] `String<String, String>`
- [ ] `Map<String, String>`

Compiling and executing correct code:

```shell
(base) onyr@aezyr:~/Documents/code/java/training/linkedin_assessment/what_variable_type_0$ javac Main.java
(base) onyr@aezyr:~/Documents/code/java/training/linkedin_assessment/what_variable_type_0$ java Main 
HUMBLE
ELEMENT
DNA
```
