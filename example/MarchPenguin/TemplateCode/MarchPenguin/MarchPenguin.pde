/* A march of the Penguins animation
   Created for Comp4050
   2022
   Template code by Angar, 2022.
*/

Penguin[] colony;
Landscape southPole = new Landscape(400);

void setup() {
  size(1000, 600);
}


void draw() {
  background(200, 0, 100);
  southPole.drawHills();//Background
  
  southPole.drawGround();//Foreground
}

void mousePressed(){

}

void mouseReleased(){
  
}

void mouseDragged(){
 
}
