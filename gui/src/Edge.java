import java.util.ArrayList;
import java.awt.Point;

class Edge extends PLib {
	Vertex source, dest;
	String id;
	ArrayList<Point> controlPoints;

	public Edge(String name, Vertex source, Vertex dest) {
		this.source = source;
		this.dest = dest;
		source.addEdge(this);
		dest.addEdge(this);
		this.id = name;
		controlPoints = new ArrayList<Point>();
	}
	
	public void display() {
		display(false);
	}
	
	public void addControlPoint(int x, int y) {
		//Point pt = new Point(x, y);
		//if (controlPoints.size()==0 || !pt.equals(controlPoints.get(controlPoints.size()-1)))
		controlPoints.add(new Point(x,y));
	}
	
	public void clearControlPoints() {
		controlPoints.clear();
	}
	
	public void bspline() {
		QuantoApplet p = QuantoApplet.p; // instance of PApplet which has all processing tools
		
		int size = controlPoints.size();
		float[] cx = new float[size];
		float[] cy = new float[size];
		
		for (int i=0;i<size;++i) {
			cx[i] = controlPoints.get(i).x;
			cy[i] = controlPoints.get(i).y;
		}
		
		p.noFill();
		p.beginShape();
		p.vertex(cx[0],cy[0]);
		int x, y, c1x, c1y, c2x, c2y;
		float tight=1.0f;
		for (int i=0;i<size-1;++i) {
			x = floor(0.5f*(cx[i]+cx[i+1]));
			y = floor(0.5f*(cy[i]+cy[i+1]));
			
			p.curveVertex(x, y);
		}
		p.vertex(cx[size-1], cy[size-1]);
		p.stroke(0);
		p.endShape();
		p.fill(0);
		/*float dx, dy, len, offX, offY;
		Point second = controlPoints.get(1);
		Point secondToLast = controlPoints.get(controlPoints.size()-2);
		
		for (int j=1; j<size-2; ++j) {
			cx[j+1] = controlPoints.get(j).x;
			cy[j+1] = controlPoints.get(j).y;
		}
		
		dx = (float)second.x - source.x;
		dy = (float)second.y - source.y;
		len = sqrt(dx * dx + dy * dy);
		offX = 8.0f * (dx / len);
		offY = 8.0f * (dy / len);
		
		// duplicate the first point
		cx[0] = source.x + offX;
		cx[1] = cx[0];
		cy[0] = source.y + offY;
		cy[1] = cy[0];
		
		dx = dest.x - (float)secondToLast.x;
		dy = dest.y - (float)secondToLast.y;
		len = sqrt(dx * dx + dy * dy);
		offX = 8.0f * (dx / len);
		offY = 8.0f * (dy / len);
		
		// duplicate the last point
		cx[size+1] = dest.x - offX;
		cx[size] = cx[size+1];
		cx[size-1] = cx[size];
		cy[size+1] = dest.y - offY;
		cy[size] = cy[size+1];
		cy[size-1] = cy[size+1];
		*/
		
		
		// CUBIC B-SPLINE
		/*
		float tx3, tx2, tx1, tx0, ty3, ty2, ty1, ty0;
		float x, y, lastX=cx[0], lastY=cy[0];
		for (int i=1; i<cx.length-2; ++i) {
		  p.ellipse(cx[i], cy[i], 3, 3);
		  tx3 = 0.1666666667f * (-cx[i-1] + 3f * cx[i] - 3f * cx[i+1] + cx[i+2]);
		  tx2 = 0.1666666667f * (3f * cx[i-1] - 6f * cx[i] + 3f * cx[i+1]);
		  tx1 = 0.1666666667f * (-3f * cx[i-1] + 3f * cx[i+1]);
		  tx0 = 0.1666666667f * (cx[i-1] + 4f * cx[i] + cx[i+1]);
		  
		  
		  ty3 = 0.1666666667f * (-cy[i-1] + 3f * cy[i] - 3f * cy[i+1] +  cy[i+2]);
		  ty2 = 0.1666666667f * (3f * cy[i-1] - 6f * cy[i] + 3f * cy[i+1]);
		  ty1 = 0.1666666667f * (-3f * cy[i-1] + 3f * cy[i+1]);
		  ty0 = 0.1666666667f * (cy[i-1] + 4f * cy[i] + cy[i+1]);
		  
		  for (float t=0.0f; t<=1.0f; t+=0.05f) {
		    x = tx3 * t * t * t + tx2 * t * t + tx1 * t + tx0;
		    y = ty3 * t * t * t + ty2 * t * t + ty1 * t + ty0;
		    
		    p.line(lastX, lastY, x, y);
		    
		    lastX = x;
		    lastY = y;
		  }
		}
		*/
		
		// QUADRATIC B-SPLINE
		/*
		float tx2, tx1, tx0, ty2, ty1, ty0;
		float x, y, lastX=cx[0], lastY=cy[0];
		for (int i=1; i<cx.length-2; ++i) {
			p.fill(0);
			p.stroke(0);
			tx2 = 0.5f * cx[i-1] - cx[i] + 0.5f * cx[i+1];
			tx1 = -cx[i-1] + cx[i];
			tx0 = 0.5f * cx[i-1] + 0.5f * cx[i];
			
			ty2 = 0.5f * cy[i-1] - cy[i] + 0.5f * cy[i+1];
			ty1 = -cy[i-1] + cy[i];
			ty0 = 0.5f * cy[i-1] + 0.5f * cy[i];
			
			for (float t=0.0f; t<1.0f; t+=0.1) {
				x = tx2 * t * t + tx1 * t + tx0;
				y = ty2 * t * t + ty1 * t + ty0;
				
				p.line(floor(lastX), floor(lastY), floor(x), floor(y));
		    
				lastX = x;
				lastY = y;
			}
		}*/
		
		
	}

	public void display(boolean inMotion) {
		QuantoApplet p = QuantoApplet.p; // instance of PApplet which has all processing tools
		
		/*int colInc = 0;
		for (Point pt : controlPoints) {
			p.stroke(colInc,0,255-colInc);
			p.fill(colInc,0,255-colInc);
			p.ellipse(pt.x, pt.y, 3, 3);
			colInc = (colInc>205) ? colInc : colInc+50;
		}
		*/
		
		float dx, dy, len, offX, offY;
		
		if (inMotion) {
			p.stroke(120,120,200);
			p.fill(120,120,200);
		} else {
			p.stroke(0);
			p.fill(0);
		}
		
		if (!inMotion && controlPoints.size()>1 && p.doSplines) {
			bspline();
			Point secondToLast = controlPoints.get(controlPoints.size()-2);
			dx = dest.x - secondToLast.x;
			dy = dest.y - secondToLast.y;
			len = sqrt(dx * dx + dy * dy);
			offX = 8.0f * (dx / len);
			offY = 8.0f * (dy / len);
		} else {
			dx = dest.x - source.x;
			dy = dest.y - source.y;
			
			len = sqrt(dx * dx + dy * dy);

			offX = 8.0f * (dx / len);
			offY = 8.0f * (dy / len);
			
			p.line(source.x + offX, source.y + offY, dest.x - offX, dest.y - offY);
		}


		float theta = acos(dx / len);
		if (dy <= 0) {
			theta = (2 * PI) - theta;
		}
		float p1x = dest.x - offX + 5.0f * cos(theta + 1.2f * PI);
		float p1y = dest.y - offY + 5.0f * sin(theta + 1.2f * PI);
		float p2x = dest.x - offX + 5.0f * cos(theta - 1.2f * PI);
		float p2y = dest.y - offY + 5.0f * sin(theta - 1.2f * PI);
		
		p.triangle(dest.x - offX, dest.y - offY, p1x, p1y, p2x, p2y);
	}
}