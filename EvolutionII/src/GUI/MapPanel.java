package GUI;
import Evolution.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MapPanel extends JPanel {

    private final RectangularMap map;
    final int width;
    final int height;
    final int scale;

    final Color [] energyColors = new Color [16];
    Graphics2D g2d;

    public MapPanel(RectangularMap map, int scale) {
        this.map = map;
        this.width = this.map.width;
        this.height = this.map.height;
        this.scale = scale;
        setPreferredSize(new Dimension(width*scale, height*scale));

        this.energyColors [0] = new Color (0,0,0);
        this.energyColors [1] = new Color (70, 3, 0);
        this.energyColors [2] = new Color (104, 6, 0);
        this.energyColors [3] = new Color (127, 25, 33);
        this.energyColors [4] = new Color (133, 34, 55);
        this.energyColors [5] = new Color (133, 48, 85);
        this.energyColors [5] = new Color (149, 58, 117);
        this.energyColors [7] = new Color (140, 75, 122);
        this.energyColors [8] = new Color (142, 90, 169);
        this.energyColors [9] = new Color (143, 113, 200);
        this.energyColors [10] = new Color (116, 93, 196);
        this.energyColors [11] = new Color (91, 101, 196);
        this.energyColors [11] = new Color (108, 140, 196);
        this.energyColors [13] = new Color (97, 164, 182);
        this.energyColors [14] = new Color (118, 195, 189);
        this.energyColors [15] = new Color (103, 182, 145);



    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
        map.anotherDay();

        g2d.setPaint(new Color(149, 178, 78));
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width*scale, height*scale);
        g2d.fill(rectangle);

        g2d.setPaint(new Color(66, 150, 74));
        Rectangle2D rectangleJungle = new Rectangle2D.Double(0, 0, this.map.jungle.width *scale, this.map.jungle.height*scale);
        g2d.fill(rectangleJungle);

        for(int x=0; x<this.map.width; x++)
            for (int y=0; y<this.map.height; y++){
                if (this.map.grassObjectAt( new Vector2d( x,y))!= null){
                    paintGrassAt(x,y);
                }
            }

        for (int i=0; i<this.map.animalsList.size(); i++){
            paintAnimal(map.animalsList.get(i));
        }

    }
    protected void paintGrassAt (int x, int y){
        double margin = this.scale/4;

        g2d.setPaint(new Color(8, 74, 0));
        Rectangle2D rectangleGrass = new Rectangle2D.Double(x*scale + margin, y*scale + margin, 2*margin, 2*margin);
        this.g2d.fill(rectangleGrass);

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(45), rectangleGrass.getX() + ((Rectangle2D.Double) rectangleGrass).width/2, rectangleGrass.getY() + ((Rectangle2D.Double) rectangleGrass).height/2);

        Shape rectangleGrass2 = tx.createTransformedShape(rectangleGrass);
        this.g2d.fill(rectangleGrass2);
    }
    protected void paintAnimal(Animal animal){

        if (animal.getEnergyDay()>=14)
            g2d.setPaint(energyColors[15]);
        else if (animal.getEnergyDay()<0){
            g2d.setPaint(energyColors[0]);
        }
        else {
            int colorNumber = (int) (Math.ceil(animal.getEnergyDay()));
            g2d.setPaint(energyColors[colorNumber]);
        }



        double margin = this.scale/6;
        int x = animal.getPosition().x;
        int y = animal.getPosition().y;

        Ellipse2D circle2 = new Ellipse2D.Double(x*scale + margin, y*scale + margin, 4*margin, 4*margin);
        g2d.fill(circle2);
    }
}
