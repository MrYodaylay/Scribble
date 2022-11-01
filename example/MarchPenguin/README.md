Note before: The example Processing sketches reproduced in this repository are available under the
Creative Commons Attribution 4.0 International License. The original repository is available at:
https://github.com/COMP4050/example_assignment. The rest of this README is unmodified from the original.

# This is an hypothetical assignment for a Processing unit.

This directory contains a directory with template code, and a directory with example submission. Not that these submissions contain various mistakes and errors. Note also that not all requirements are (easily) testable. They are illustative for what requirement may say, regardless of whether they can be turned in to an easy test.


## The assignment

You are given some skeleton code. It contains the beginning of a processing program to animate a march of some some penguins. You will find the main tab, plus classes for the Landscape, the Penguin and the Beak. The Landscape is finished, the Penguin partly finished, and the Beak mostly unfinished. 

The task is to finish the animation given the folowing requirements

### Requirements
* Main Tab
  * Create a colony of 7 Penguins. The intial x-position for each penguin should be random, the y-position at groundLevel of the southPole. The intial velocity should be chosen randomly between 0.5 and 1.5).
  * Use the Landscape for the SouthPole. The ground is set at 2/3th of the screen.
  * Inbetween the hills and the ground, display the penguins, and have them walk over the south pole with the colony.
  * If you press the mouse, it should select one penguin.
  * If you release the mouse, it should deselct all penguins.
  * If you drag the mouse, it should move the penguins. (Only the selected penguins. To move a penguin only if it is selected, is however part of the penguin implementation and should not be implemented here)
* Beak Class
  * The constructor should set the initial position, and size of the beak.
  * The display method should display the beak. Use part of the Penguin display method that draws the beak. You will have to modify it.
  * The move method should move the beak when the squeakConter is strictly positive. This means that the beak should open up and then close again. The upper half should rotate up to 45 degrees above, and the lower half 45 degrees below the 0 degree line. Rotate around the left end of the beak. Use the attributes angle and angularSpeed to achieve the effect. Each time the beak is closed decrement the squeakCounter.
  * The squeak method sets the squeakCounter to 3.
* Lanscape Class
  * Don't touch the Lanscape class.
* Penguin
  * The constructor and the display method are given. The Pengin itself is about SIZE wide, and has head that is a circle of diameter SIZE, and a body that fits - with the exception of the tail, in a rectangle that is SIZE wide and 2 SIZE high. Note that the x,y position denotes the center of the circle that is the head, which is 2 SIZE above the feet.
  * The Penguin should have a beak object. Initialise the beak to be 0.45 SIZE to the right from the center of the head. Move the part of display that draws the beak to the display method of the beak. Call the display method of the Beak object instead.
  * The walk method should do the following:
    * If the Penguin is not selected add dx to x. Update the y position to be 2 SIZE above the goundLevel of the land (Remember, the head is 2 SIZE above the head). 
    * If the Penguin is close to peers the speed dx should be reduced by 5%, otherwise the speed dx should converge slowly to the target velocity. 
  * The isOver method should return true if they are on the circle that is the head, or the rectangle that is the body.
  * The isClose method should return true is any of the other peer's head positions if less than 2 SIZE from the penguins head position, and in front of the penguin.
  * The select method should set isSelected to true if x and y is over the penguin, and to false other wise. It should retrun isSelected.
  * The deselect method should set isSelected to false.
  * The drag method should assign the values of x and y to the position of the penguin, if the penguin is selected.
  
  
  


