class Penguin {
  float x, y, dx;
  float target_velocity = 0;
  int SIZE = 50;
  boolean isSelected =  false;
  

  //Constructor to set the initial position, and the intial and target velocity.
  Penguin(float x, float y, float dx) {
    this.x = x;
    this.y = y - 2*SIZE;
    this.target_velocity = dx;
    this.dx = dx;    
  }


  void display() {
    pushMatrix();
    translate(this.x,this.y);    
    //Black part
    fill(0);
    stroke(1);
    strokeWeight(2);
    ellipse(0, 0, SIZE, SIZE);
    ellipse(0,SIZE, SIZE, 2*SIZE);
    rect(-SIZE/2,SIZE, SIZE, SIZE);
    triangle(0, 0.75*SIZE, 0, 2*SIZE, -SIZE, 2*SIZE);
  
    //The beak
    fill(200, 150, 100);
    stroke(210, 160, 120);
    pushMatrix();
    translate(SIZE*0.45, 0);
    triangle(0, 0, 0, SIZE/2*0.45, SIZE/2, 0);
    triangle(0, 0, 0, -SIZE/2*0.45, SIZE/2, 0);
    popMatrix();
    
    //The white part
    fill(255);
    noStroke();
    arc(0, 0, SIZE, SIZE, -QUARTER_PI, QUARTER_PI, OPEN);
    arc(SIZE*1.44, 0, 3*SIZE, SIZE, 3*QUARTER_PI, 5*QUARTER_PI, OPEN);
    arc(0, SIZE, SIZE, 2*SIZE, -QUARTER_PI, QUARTER_PI);
    
    //The eyes
    fill(100, 100, 200);
    ellipse(SIZE/4, -SIZE/5, SIZE/6, SIZE/6);
    popMatrix();
  }

  void walk(Landscape land, Penguin[] peers) {
   
  }
  
  boolean isOver(float x, float y) {
    boolean result = dist(x, y, this.x, this.y)<=SIZE/2;
    result = result || ((x>=this.x-SIZE/2) && (x<=this.x+SIZE/2)
      && (y>=this.y) && (this.y<=this.y+2*SIZE));
    return result;
  }

  boolean isClose(Penguin[] peers) {
    boolean result = false;
    for (int i = 0; i<peers.length; i++) {
      if (this != peers[i]
        && dist(peers[i].x, peers[i].y, this.x, this.y)<=2*SIZE
        && peers[i].x>this.x) {
        result =true;
      }
    }
    return result;
  }

  boolean select(float x, float y) {
    return true;
  }

  void deSelect() {
  }

  void drag(float x, float y) {
    
  }


}
