// Ting der kunne gøres bedre i en ny version:
/**
 * Opret tiles som class / objekter
 * Måske opret tile locations som 2d int[][]
 * Opret fjender
 * Opret flere stages
 * Lær at bruge sprites / gifs til effekter
 * Omskriv collision detection under keyevents til at bruge intersect()?
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

// Hvis man ændrer Frame til JFrame slettes den forrige position af den røde tile ikke ?!:
// Er fixet ved at bruge "retning" til at slette bag player...

public class Drawing extends JFrame implements KeyListener {

    // tile størrelse - dx og dy SKAL være større end 30, men må gerne være forskellige.
    // Vælg x-y kombinationer som går op i vindue størrelse (pt 600). Ikke relevant - vinduestørrelse er nu auto baseret på tile locations...
    // opdate: SKAL være 50 pt. pg af png billeder ikke skaleres...
    final int dx = 50;
    final int dy = 50;

    // rød tile start position:
    int x = dx * 5;
    int y = dy * 1;

    // x-y koordinater til væg-tiles. Udbyg arrays for at bygge vægge i din maze:
    /**
     * kan man hive dx og dy ud af nedenstående? ja - har oprettet multiplyArrayValuesBy()
     * kan man oprette yKoordinater udfra xKoordinater? ja - har oprettet create_yKoordinater()
     * tjek video om collision detection for at lave en fjende?
     *
    */
    // int[] xKoordinater = {2*dx,3*dx,4*dx,5*dx,6*dx,7*dx,8*dx,9*dx,10*dx,11*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,1*dx,2*dx,3*dx,4*dx,5*dx,6*dx,7*dx,8*dx,9*dx,10*dx,11*dx,12*dx,8*dx,9*dx,10*dx,11*dx,12*dx,12*dx,12*dx,4*dx,12*dx};
    // int[] yKoordinater = {12*dy,12*dy,12*dy,12*dy,12*dy,12*dy,12*dy,12*dy,12*dy,12*dy,1*dy,2*dy,3*dy,4*dy,5*dy,6*dy,7*dy,8*dy,9*dy,10*dy,11*dy,12*dy,1*dy,1*dy,1*dy,1*dy,3*dy,1*dy,1*dy,1*dy,1*dy,1*dy,1*dy,1*dy,8*dy,8*dy,8*dy,8*dy,8*dy,9*dy,10*dy,4*dy,12*dy};

    // TEST - hvis nedenstående ikke bliver godt, prøv da med 2d int array!:
            // 1,2,3,4,6,7,8,9,10,11,12

    int[] xKoordinater = {
            1,2,3,4,6,7,8,9,10,11,12, //Række 1
            1,12, //Række 2
            1,3,4,5,6,7,8,9,10,12, //Række 3
            1,4,10,12, //Række 4
            1,2,4,6,7,8,10,12, //Række 5
            1,4,8,10,12, //Række 6
            1,3,4,6,8,10,12, //Række 7
            1,3,6,8,10,12, //Række 8
            1,3,5,6,8,12, //Række 9
            1,3,5,6,8,9,10,11,12, //Række 10
            1,5,6, //Række 11
            1,2,3,4,5,6,7,8,9,10,11,12, //Række 12
    };
    int[] yKoordinater = create_yKoordinater(xKoordinater);
    // funktionen create_yKoordinater skaber nedenstående int array:
    /*int[] yKoordinater = {
            1,1,1,1,1,1,1,1,1,1,1, //Række 1
            2,2, //Række 2
            3,3,3,3,3,3,3,3,3,3, //Række 3
            4,4,4,4, //Række 4
            5,5,5,5,5,5,5,5, //Række 5
            6,6,6,6,6, //Række 6
            7,7,7,7,7,7,7, //Række 7
            8,8,8,8,8,8, //Række 8
            9,9,9,9,9,9, //Række 9
            10,10,10,10,10,10,10,10,10, //Række 10
            11,11,11, //Række 11
            12,12,12,12,12,12,12,12,12,12,12,12 //Række 12
    };*/
    int[] xKoordinaterTiles = multiplyArrayValuesBy(xKoordinater,dx);
    int[] yKoordinaterTiles = multiplyArrayValuesBy(yKoordinater,dy);
    // TEST SLUT


    // variabel til test af fremtidig position:
    int nyPosX = 0;
    int nyPosY = 0;
    // x-y koordinater for målet:
    int goalPositionX = dx*12;
    int goalPositionY = dy*11;
    // vindue størrelse:
    int windowSizeX = Arrays.stream(xKoordinaterTiles).max().getAsInt() + dx;
    int windowSizeY = Arrays.stream(yKoordinaterTiles).max().getAsInt() + dy;
    // bruges ikke pt.:
    //int windowSizeX = 600;
    //int windowSizeY = 600;
    // vindue padding:
    int windowPadding = 100;
    // den sorte kants størrelse:
    int rectSizeX = windowSizeX - dx;
    int rectSizeY = windowSizeY - dy;
    // retning vi kom fra (for at slette rød tile bag os):
    int retningX = 0;
    int retningY = 0;

    public Drawing() {
        setTitle("Game01");
        setSize(windowSizeX + windowPadding,windowSizeY + windowPadding);
        // mangler panel for at sætte background til picture:
        setBackground(Color.WHITE);

        // add(new JLabel(new ImageIcon("src/package/face-smile.png"))).show();

        // herunder icon virker ikke - ville ellers løse problem med dynamisk icon-størrelse:
        /*ImageIcon myIcon = new ImageIcon("src/package/face-smile.png");
        JLabel myIconLabel = new JLabel(myIcon);
        myIconLabel.setBounds(800, 800, 50, 50);
        setLayout(new FlowLayout());

        add(myIconLabel);
        myIconLabel.setVisible(true);
        */
        // tilføj key event listener:
        addKeyListener(this);
        // tilføj vindue closing event listener:
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        // tilføj vindue resize event listener:
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                // Resize Maze border:
                //windowSizeX = getWidth()-dx*2;
                //windowSizeY = getHeight()-dy*2;
                //repaint();
            }
        });
    }

    public void opretMazeTiles(Graphics g, int [] xKoordinaterTiles, int [] yKoordinaterTiles) {
        g.setColor(Color.DARK_GRAY);
        Toolkit t=Toolkit.getDefaultToolkit();
        Image image = t.getImage("src/package/hedge.png");
        for (int i = 0; i < xKoordinaterTiles.length; i++) {
            // Udelad "" i understående for automatisk at "lukke" mazen bag dig, når du tager et stridt ind i mazen:
            if (xKoordinaterTiles[i] == goalPositionX && yKoordinaterTiles[i] == goalPositionY) { //|| (xKoordinaterTiles[i] == x && yKoordinaterTiles[i] == y))
                    // Gør intet: Overskriv ikke player eller target med tiles...
                System.out.println("player tile!");
                System.out.println(xKoordinaterTiles[i]);
                System.out.println(yKoordinaterTiles[i]);
            } else {
                // fjern gerne grå tiles herunder - de bruges ikke...
                g.fillRect(xKoordinaterTiles[i],yKoordinaterTiles[i],dx,dy);
                // overskriv grå væg-tiles med hedge.png:
                g.drawImage(image, xKoordinaterTiles[i],yKoordinaterTiles[i],this);
            }
        }
    }

    public void paint(Graphics g){
        // tegn label - husk at labels har x værdi og y værdi sat til nederste venstre hjørne... altså labels vokser i størrelse fra bunden af:

        g.setColor(Color.BLACK);
        g.drawString("Find den blå firkant!",dx,dy);
        // tegn kant:
        g.setColor(Color.BLACK);
        g.drawRect(dx,dy,rectSizeX,rectSizeY);
        // ryd tile bag player:
        g.setColor(Color.WHITE);
        g.fillRect(x-retningX, y-retningY,dx,dy);
        // tegn rød player tile:
        // Fjern evt rød player tile senere - den overskrives af image...
        g.setColor(Color.RED);
        g.fillRect(x,y,dx,dy);
        // tegn blå mål tile
        g.setColor(Color.BLUE);
        g.fillRect(goalPositionX,goalPositionY,dx,dy);
        // vis rød tile er i mål:
        boolean goalReached = (x == goalPositionX && y == goalPositionY);
        if (goalReached) {
            JOptionPane.showMessageDialog(null,"Du klarede det!: ");
        }
        // tegn alle vægge:
        // PROBLEM: Alle tiles repaintes hver gang player tager et skridt!
        opretMazeTiles(g, xKoordinaterTiles, yKoordinaterTiles);

        // overskriv rød tile med smiley png:
        Toolkit t=Toolkit.getDefaultToolkit();
        Image image = t.getImage("src/package/face-smile.png");
        g.drawImage(image, x,y,this);
    }

/*
    public boolean goalReached() {
        if (x == goalPositionX && y == goalPositionY) {
            return true;
        } else {
            return false;
        }
    }
    ERSTATTET AF:
    boolean goalReached = (x == goalPositionX && y == goalPositionY);
*/

    public static void main(String[] args) {
        Drawing maze = new Drawing();
        maze.show(); // alternativ?
    }

    // bruges ved tryk ned:
    @Override
    public void keyPressed(KeyEvent e) {
        // SKAL anvende 16-bit wav eller AIFF lyd for at virke - konverter evt lyd med https://www.audacityteam.org/download/windows/:
        String filepath = "Footsteps.wav";
        SoundPlayer myPlayer = new SoundPlayer();

        // sæt nye tile-koordinater ved piletryk:
        switch (e.getKeyCode()) {
            // key events håndterer collision detection:
            case KeyEvent.VK_UP:
                // stop når rød tile når den nederste kant af grå tile (eller kanten af den sorte ramme):
                // der sker ikke noget med x når vi går til op/ned:
                nyPosX = x;
                nyPosY = y - dy;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean upHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        upHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        upHasTile = true;
                    }
                }
                if (upHasTile) {
                    break;
                } else {
                    y = y - dy;
                    retningY = -dy;
                    retningX = 0;
                    myPlayer.playMusic(filepath);
                }
                break;

            case KeyEvent.VK_DOWN:
                // stop når rød tile når den øverste kant af grå tile (eller kanten af den sorte ramme):
                // der sker ikke noget med x når vi går til op/ned:
                nyPosX = x;
                nyPosY = y + dy;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean downHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        downHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        downHasTile = true;
                    }
                }
                if (downHasTile) {
                    break;
                } else {
                    y = y + dy;
                    retningY = dy;
                    retningX = 0;
                    myPlayer.playMusic(filepath);
                }
                break;

            case KeyEvent.VK_LEFT:
                // stop når rød tile når den højre kant af grå tile (eller kanten af den sorte ramme):
                nyPosX = x - dx;
                // der sker ikke noget med y når vi går højre/venstre:
                nyPosY = y;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean leftHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        leftHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        leftHasTile = true;
                    }
                }
                if (leftHasTile) {
                    break;
                } else {
                    x = x - dx;
                    retningX = -dx;
                    retningY = 0;
                    myPlayer.playMusic(filepath);
                }
                break;

            case KeyEvent.VK_RIGHT:
                // stop når rød firkant når venstre kant af grå tile (eller kanten af den sorte ramme):
                nyPosX = x + dx;
                // der sker ikke noget med y når vi går højre/venstre:
                nyPosY = y;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean rightHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        rightHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        rightHasTile = true;
                    }
                }
                if (rightHasTile) {
                    break;
                } else {
                    x = x + dx;
                    retningX = dx;
                    retningY = 0;
                    myPlayer.playMusic(filepath);
                }
                break;
        }
        // når nye koordinater er sat, recall paint:
        repaint();
    }

    // bruges ved tryk ned og slip:
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // bruges ved slip:
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public static int[] multiplyArrayValuesBy(int[] inputArray, int multiplier) {
        int[] resultArray = new int[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            resultArray[i] = inputArray[i] * multiplier;
        }
        return resultArray;
    }

    public static int[] create_yKoordinater(int[] intArray) {
        int count1 = 0;
        int[] result = new int[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] == 1) {
                count1++;
            }
            result[i] = count1;
        }
        return result;
    }
}

/*

    int[] xKoordinater = {
            1,2,3,4,6,7,8,9,10,11,12, //Række 1
            1,12, //Række 2
            1,3,4,5,6,7,8,9,10,12, //Række 3
            1,4,10,12, //Række 4
            1,2,4,6,7,8,10,12, //Række 5
            1,4,8,10,12, //Række 6
            1,3,4,6,8,10,12, //Række 7
            1,3,6,8,10,12, //Række 8
            1,3,5,6,8,12, //Række 9
            1,3,5,6,8,9,10,11,12, //Række 10
            1,5,6, //Række 11
            1,2,3,4,5,6,7,8,9,10,11,12 //Række 12
    };
    int[] yKoordinater = {
            1,1,1,1,1,1,1,1,1,1,1, //Række 1
            2,2, //Række 2
            3,3,3,3,3,3,3,3,3,3, //Række 3
            4,4,4,4, //Række 4
            5,5,5,5,5,5,5,5, //Række 5
            6,6,6,6,6, //Række 6
            7,7,7,7,7,7,7, //Række 7
            8,8,8,8,8,8, //Række 8
            9,9,9,9,9,9, //Række 9
            10,10,10,10,10,10,10,10,10, //Række 10
            11,11,11, //Række 11
            12,12,12,12,12,12,12,12,12,12,12,12 //Række 12
    };
    */
