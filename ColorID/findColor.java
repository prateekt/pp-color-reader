/**
 * @(#)main.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */
 
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class findColor implements ActionListener, MouseListener
{
	//GUI STUFF
	private BufferedImage image;    // the rasterized image
    private JFrame frame;           // on-screen view
	
	//Private member variables.
	private Picture pic;			//Current picture
	private int mouse_x;			//X coordinate of most recent mouse click
	private int mouse_y;			//Y coordinate of most recent mouse click
	private String dbPath;			//location of the database
	private String filename;		//location of the current picture
	private String text;			//text to be displayed
	private ColorDB db;				//Database of ColorNames which we need to check
	private boolean pic_changed = false;		//Boolean for if we have loaded another picture

    public findColor(String db_in) {
    	//Set the member variables
    	db = new ColorDB(db_in);
    	text = "ajf;ljfea";
    	pic = new Picture("banana_ripeningchart.jpg"); //Default picture, TODO: Should change to handle no picture
    	/****************************************
    	 * Initialize GUI						*
    	 *		Create Form						*
    	 *		Mouse Listener					*
    	 *		Loading Picture					*
    	 *		Loop							*
    	 *			-On click call getColor		* 
    	 *			-Load new picture(getImage)	*
    	 ****************************************/
    }
    
    /* Uses the private members pic and the mouseclick coordinates *
     * Based on results of functions called alters the GUI to      *
     * display the color at the coordinates						   */
    private void getColor()
    {
    	//Local variables
    	Color pic_color;			//Average color around clicked pixel
    	String color_name;			//
    	
    	/*Call average pixel on pic and the x,y coordinates to find
    	 *the average color around the passed pixel*/
    	 pic_color = averagePixel(pic, mouse_x, mouse_y);
    	 
    	/*Convert the color found into the appropriate english name*/
    	color_name = db.ColorToName(pic_color);
    	
    	/*Now that we have found the color we want to output alter
    	 *the GUI to display the color to the user*/
    	
    	return;
    }
    
    /*take (x,y) location in pic and return the average color 	 *
     *by refering the pixels around it						     */
    private Color averagePixel(Picture pic, int x, int y)
    {
    	/*Set the lower and upper limint for reference
    	 */
    	int lower = y + 1;
    	if (lower >= pic.height()) lower = pic.height()-1;
    	int upper = y - 1;
    	if (upper < 0) upper = 0;
    	int left = x - 1;
    	if (left < 0) left = 0;
    	int right = x + 1;
    	if (right < pic.width()) right = pic.width()-1;
    	
    	/*Create local RGB variables to store the	*
    	 *values, using floats for future division	*/
    	float green = 0, blue = 0, red = 0;
    	
    	/*Scan all pixels around (x, y) and store		*
    	 *their RGB values, changing to floats			*/
    	for(int yCounter = lower; yCounter <= upper; yCounter++)
    	{
    		for(int xCounter = left; xCounter <= right; xCounter++)
    		{
    			green += (float)(pic.get(x, y).getGreen());
    			blue += (float)(pic.get(x, y).getBlue());
    			red += (float)(pic.get(x, y).getRed());
    		}
    	}
    	
    	/*Use the resulting RGB values to create* 
    	 *an average color						*/
    	Color average = new Color(red/((right-left)*(upper-lower)),
    								 green/((right-left)*(upper-lower)),
    								 blue/((right-left)*(upper-lower)));
    	
    	/*That is what we want*/
   		return average;
    }
    
    //set the text to be displayed
    public void setText(String ss){
    	text = ss;
    }
    
    /*display the graphic user interface
     */
    public void GUI()
    {
    	if (frame == null)			//If frame has not been initialized yet
    	{
    		frame = new JFrame();	//Create a new Jframe
    		
    		//Set menu bar
    		JMenuBar menuBar = new JMenuBar();		//Add the top menu
    		JMenu menu = new JMenu("File");			//Create a new menu item
            menuBar.add(menu);						//add that menu item to the menu
            JMenuItem menuItem1 = new JMenuItem(" Load...   ");	//Create sub category to the File tab
            menuItem1.addActionListener(this);		//Add a listener to the menu for when the load button is clicked
            menu.add(menuItem1);					//add Load sub category to the menu
            frame.setJMenuBar(menuBar);				//Add menu bar to the frame
            
            Container content = frame.getContentPane();
            content.add(new JLabel(text));				//display text
            content.add(pic.getJLabel());			//display image
            frame.setContentPane(content);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//default close operation
            frame.setTitle("Color Identifier");				//title
            frame.addMouseListener(content);				
            
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
    	}
    	if (pic_changed)
    	{
    		frame.setContentPane(pic.getJLabel());
    		frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
    	}
    	frame.repaint();				//draw
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        FileDialog chooser = new FileDialog(frame,
                             "Use a .png or .jpg extension", FileDialog.LOAD);
        chooser.setVisible(true);
        if (chooser.getFile() != null) 
        {
            pic = new Picture(chooser.getDirectory() + File.separator + chooser.getFile());
            System.out.println("got to new picture");
            pic_changed = true;
            GUI();
        }
    }

	//mouse detection
	public void mouseClicked(MouseEvent e)
	{
		mouse_x = e.getX();
		mouse_y = e.getY();
		System.out.println("mouse x click Coordinate = " + mouse_x);
		System.out.println("mouse y click Coordinate = " + mouse_y);
		
		// Update to the current color
		getColor();
	}
	 public void mousePressed(MouseEvent e) {
        return;
    }
    
    public void mouseReleased(MouseEvent e) {
        return;
    }
    
    public void mouseEntered(MouseEvent e) {
        return;
    }
    
    public void mouseExited(MouseEvent e) {
        return;
    }
   
}