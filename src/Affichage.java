import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Affichage extends JFrame implements ActionListener, MouseWheelListener, MouseListener{

	Point centre;
	JPanel aff, input;
	JTextField ite, re, im, zoo;
	JButton genererM, genererL, centrer, couleur;
	JLabel t0, t1, t2, t3;
	BufferedImage img;
	Graphics gImg;
	int ordre, zoom, scroll, coul, oldZoom;
	boolean lastJulia, running;
	
	public Affichage () {
		super("Julia - Tatou - Mandelbrot");
		setSize(1800, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		aff= new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, this);
			}
		};
		input= new JPanel();
		
		aff.addMouseListener(this);
		addMouseWheelListener(this);
		
		ite= new JTextField("150", 5);
		re= new JTextField("0.285", 10);
		im= new JTextField("0.01", 10);
		zoo= new JTextField("400", 5);
		
		t0= new JLabel("Zoom :");
		t1= new JLabel("Ordre :");
		t2= new JLabel("   c = ");
		t3= new JLabel(" + i * ");
		
		genererM= new JButton("Mandelbrot");
		genererL= new JButton("Julia");
		centrer= new JButton("Centrer");
		couleur= new JButton("Changer la couleur");
		genererM.addActionListener(this);
		genererL.addActionListener(this);
		centrer.addActionListener(this);
		couleur.addActionListener(this);
		
		input.add(genererL);
		input.add(genererM);
		input.add(t0);
		input.add(zoo);
		input.add(t1);
		input.add(ite);
		input.add(t2);
		input.add(re);
		input.add(t3);
		input.add(im);
		input.add(centrer);
		input.add(couleur);
		
		add(aff, BorderLayout.CENTER);
		add(input, BorderLayout.SOUTH);
		
		scroll= 0;
		coul= 0;
		running= false;
		
		setVisible(true);
		centre= new Point(aff.getWidth()/2, aff.getHeight()/2);
		zoom= 400;
	}
	
	public static void main (String[] args) {
		new Affichage();
	}
	
	public Point2D.Double repereCartesien (Point2D.Double a) {
		return new Point2D.Double(a.x-centre.x, centre.y-a.y);
	}
	
	public Point repereFenetre (Point a) {
		return new Point(a.x+centre.x, centre.y-a.y);
	}
	
	public void genMandelbrot () {
		Complexe c, z;
		int avancement;
		for (int i= 0; i<img.getHeight(); i++) {
			for (int j= 0; j<img.getWidth(); j++) {
				avancement= ordre;
				z= new Complexe(0, 0);
				c= new Complexe(repereCartesien(new Point2D.Double(j, i)));
				c= c.division(new Complexe(zoom, 0));
				for (int k= 0; k<ordre; k++) {
					z= z.julia(c);
					if (z.module() >= 2) {
						avancement= k;
						break;
					}
				}
				switch (coul) {
					case 0:
						if (avancement == ordre) {
							gImg.setColor(new Color(0, 0, 66));
						} else {
							gImg.setColor(new Color((int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*Math.exp(-avancement*5/(double)ordre))));
						}
						break;
					case 1:
						if (avancement == ordre) {
							gImg.setColor(new Color(33, 00, 66));
						} else {
							gImg.setColor(new Color((int)(127+128*(1-Math.exp(-avancement*5/(double)ordre))), 0, (int)(255*Math.exp(-avancement*5/(double)ordre))));
						}
						break;
					case 2:
						if (avancement == ordre) {
							gImg.setColor(new Color(0, 66, 0));
						} else {
							gImg.setColor(new Color((int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*Math.exp(-avancement*5/(double)ordre)), (int)(255*(1-Math.exp(-avancement*5/(double)ordre)))));
						}
						break;
					case 3: 
						if (avancement == ordre) {
							gImg.setColor(new Color(66, 0, 0));
						} else {
							gImg.setColor(new Color((int)(255*Math.exp(-avancement*5/(double)ordre)), (int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*(1-Math.exp(-avancement*5/(double)ordre)))));
						}
						break;
					default:
						return;
				}
				gImg.drawLine(j, i, j, i);
			}
		}
		aff.repaint();
	}
	
	public void genJulia (Complexe c) {
		int avancement;
		Complexe z;
		for (int i= 0; i<img.getHeight(); i++) {
			for (int j= 0; j<img.getWidth(); j++) {
				avancement= ordre;
				z= new Complexe(repereCartesien(new Point2D.Double(j, i)));
				z= z.division(new Complexe(zoom, 0));
				for (int k= 0; k<ordre; k++) {
					z= z.julia(c);
					if (z.module() >= 2) {
						avancement= k;
						break;
					}
				}
				switch (coul) {
					case 0:
						if (avancement == ordre) {
							gImg.setColor(new Color(0, 0, 66));
						} else {
							gImg.setColor(new Color((int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*Math.exp(-avancement*5/(double)ordre))));
						}
						break;
					case 1:
						if (avancement == ordre) {
							gImg.setColor(new Color(33, 00, 66));
						} else {
							gImg.setColor(new Color((int)(127+128*(1-Math.exp(-avancement*5/(double)ordre))), 0, (int)(255*Math.exp(-avancement*5/(double)ordre))));
						}
						break;
					case 2:
						if (avancement == ordre) {
							gImg.setColor(new Color(0, 66, 0));
						} else {
							gImg.setColor(new Color((int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*Math.exp(-avancement*5/(double)ordre)), (int)(255*(1-Math.exp(-avancement*5/(double)ordre)))));
						}
						break;
					case 3: 
						if (avancement == ordre) {
							gImg.setColor(new Color(66, 0, 0));
						} else {
							gImg.setColor(new Color((int)(255*Math.exp(-avancement*5/(double)ordre)), (int)(255*(1-Math.exp(-avancement*5/(double)ordre))), (int)(255*(1-Math.exp(-avancement*5/(double)ordre)))));
						}
						break;
					default:
						return;
				}
				gImg.drawLine(j, i, j, i);
			}
		}
		aff.repaint();
	}
	
	public void actionPerformed (ActionEvent e) {
		img= new BufferedImage(aff.getWidth(), aff.getHeight(), BufferedImage.TYPE_INT_RGB);
		gImg= img.getGraphics();
		ordre= Integer.parseInt(ite.getText());
		oldZoom= zoom;
		zoom= Integer.parseInt(zoo.getText());
		scroll= 0;
		if (oldZoom != zoom) {
			Complexe z= new Complexe(repereCartesien(new Point2D.Double(aff.getWidth()/2, aff.getHeight()/2)));
			z= z.division(new Complexe(oldZoom, 0)).multiplication(new Complexe(zoom, 0));
			centre.x= centre.x+aff.getWidth()/2-repereFenetre(z.toPoint()).x;
			centre.y= centre.y+aff.getHeight()/2-repereFenetre(z.toPoint()).y;
		}
		if (e.getSource() == genererL) {
			lastJulia= true;
			genJulia(new Complexe(Double.parseDouble(re.getText()), Double.parseDouble(im.getText())));
		} else if (e.getSource() == genererM) {
			lastJulia= false;
			genMandelbrot();
		} else if (e.getSource() == centrer){
			centre= new Point(aff.getWidth()/2, aff.getHeight()/2);
			if (lastJulia) {
				genJulia(new Complexe(Double.parseDouble(re.getText()), Double.parseDouble(im.getText())));
			} else {
				genMandelbrot();
			}
		} else {
			coul++;
			coul%= 4;
			if (lastJulia) {
				genJulia(new Complexe(Double.parseDouble(re.getText()), Double.parseDouble(im.getText())));
			} else {
				genMandelbrot();
			}
		}
	}
	
	
	public void mousePressed (MouseEvent e) {
		centre.x= centre.x+aff.getWidth()/2-e.getX();
		centre.y= centre.y+aff.getHeight()/2-e.getY();
		if (lastJulia) {
			genJulia(new Complexe(Double.parseDouble(re.getText()), Double.parseDouble(im.getText())));
		} else {
			genMandelbrot();
		}
	}
	
	public void mouseWheelMoved (MouseWheelEvent e) {
		scroll+= e.getWheelRotation();
		if (Math.abs(scroll) >= 5) {
			oldZoom= zoom;
			if (scroll < 0) {
				zoom*= 0.7;
			} else {
				zoom*= 1.3;
			}
			zoo.setText(String.format("%d", zoom));
			if (oldZoom != zoom) {
				Complexe z= new Complexe(repereCartesien(new Point2D.Double(aff.getWidth()/2, aff.getHeight()/2)));
				z= z.division(new Complexe(oldZoom, 0)).multiplication(new Complexe(zoom, 0));
				centre.x= centre.x+aff.getWidth()/2-repereFenetre(z.toPoint()).x;
				centre.y= centre.y+aff.getHeight()/2-repereFenetre(z.toPoint()).y;
			}
			if (!running) {
				running= true;
				if (lastJulia) {
					genJulia(new Complexe(Double.parseDouble(re.getText()), Double.parseDouble(im.getText())));
				} else {
					genMandelbrot();
				}
				running= false;
			}
			scroll= 0;
		}
	}
	
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseClicked (MouseEvent e) {}
	
}
