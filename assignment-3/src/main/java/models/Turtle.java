package models;


public class Turtle {
    private Point currentLocation = new Point();
    private int directionDegrees = 0;
    private boolean isPenUp = true;


    public Turtle() {}


    public Turtle(Point currentLocation, int directionDegrees,
                  boolean isPenUp) {
        this.currentLocation = currentLocation;
        this.directionDegrees = directionDegrees;
        this.isPenUp = isPenUp;
    }


    public Point location() {
        return currentLocation;
    }

    public double direction() {
        return directionDegrees;
    }


    public void penUp() {
        isPenUp = true;
    }

    public void penDown() {
        isPenUp = false;
    }

    public boolean isPenUp() {
        return isPenUp;
    }

    public void move(double distance) {
        double radians = directionDegrees * Math.PI / 180;
        double deltaX = Math.cos(radians) * distance;
        double deltaY = Math.sin(radians) * distance;

        double newX = currentLocation.getX() + deltaX;
        double newY = currentLocation.getY() + deltaY;

        currentLocation = new Point(newX, newY);
    }

    public void turn(int degreesToTurn) {
        directionDegrees += degreesToTurn;

        // Rollover if the direction goes over 360 degrees
        if(directionDegrees >= 360) {
            directionDegrees %= 360;
        }
    }

    public TurtleMemento createMemento() {
        return new TurtleMemento(this);
    }

    public void restoreFromMemento(TurtleMemento memento) {
        Turtle stateTurtle = memento.getStateTurtle();
        directionDegrees = stateTurtle.directionDegrees;
        currentLocation = new Point(stateTurtle.location());
        isPenUp = stateTurtle.isPenUp;
    }


    @Override
    public boolean equals(Object anotherObject) {
        if(anotherObject instanceof Turtle) {
            Turtle anotherTurtle = (Turtle) anotherObject;

            return directionDegrees == anotherTurtle.directionDegrees &&
                    currentLocation.equals(anotherTurtle.currentLocation) &&
                    isPenUp == anotherTurtle.isPenUp;
        } else return false;
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = (31 * result) + currentLocation.hashCode();
        result = (31 * result) + (isPenUp ? 1 : 0);
        result = (31 * result) + directionDegrees;

        return result;
    }


    public class TurtleMemento {
        private Turtle stateTurtle;


        private TurtleMemento(Turtle inputTurtle) {
            stateTurtle = new Turtle();
            stateTurtle.directionDegrees = inputTurtle.directionDegrees;
            stateTurtle.currentLocation = new Point(inputTurtle.location());
            stateTurtle.isPenUp = inputTurtle.isPenUp;
        }

        private Turtle getStateTurtle() {
            return stateTurtle;
        }


        @Override
        public boolean equals(Object anotherObject) {
            if(anotherObject instanceof TurtleMemento) {
                return stateTurtle.equals(
                        ((TurtleMemento) anotherObject).stateTurtle);
            } else return false;
        }

        @Override
        public int hashCode() {
            int result = 31;

            result *= stateTurtle.hashCode();

            return result;
        }
    }
}
