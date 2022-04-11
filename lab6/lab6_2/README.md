During the analysis, Sonar found a bug in the Dip class, that the Random object was not reused. 

It provided the following explanation:

"Creating a new `Random` object each time a random value is needed is inefficient and may produce numbers which are not random depending
on the JDK. For better efficiency and randomness, create a single `Random`, then store, and reuse it.

The `Random()` constructor tries to set the seed with a 
distinct value every time. However there is no guarantee that the seed 
will be
random or even uniformly distributed. Some JDK will use the current time
 as seed, which makes the generated numbers not random at all.

This rule finds cases where a new `Random` is created each time a method is invoked."

This led to a D score in reliability.



In addition, it also declared the use of the pseudo-RNG a risk, telling us to make sure using it is safe here. This led to 0% of the security hotspots being reviewed, causing an abysmal security review grade: E.



As for maintainability, 24 code smells were found, 9 major, 4 minor and 11 info.



| Issue              | Problem description                                                                                                                                                                                                                                                                                                                                                                                                                                             | How to solve                                                                             |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| Bug                | "Random" objects should be reused. Creating a new Random object each time a random value is needed is inefficient and may produce numbers which are not random depending on the JDK.                                                                                                                                                                                                                                                                            | Create a single Random, then store, and reuse it.                                        |
| Code smell (major) | A for loop stop condition should test the loop counter against an invariant value (i.e. one that is true at both the beginning and ending of every loop iteration). Ideally, this means that the stop condition is set to a local variable just before the loop begins. Stop conditions that are not invariant are slightly less efficient, as well as being difficult to understand and maintain, and likely lead to the introduction of errors in the future. | Refactor the code in order to not assign to this loop counter from within the loop body. |
| Code smell (minor) | The imports part of a file should be handled by the Integrated Development Environment (IDE), not manually by the developer.<br/><br/>Unused and useless imports should not occur if that is the case.<br/><br/>Leaving them in reduces the code’s readability, since their presence can be confusing.                                                                                                                                                      | Remove this unused import 'java.util.Arrays'.                                            |


