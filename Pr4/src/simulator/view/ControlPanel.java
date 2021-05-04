package simulator.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver, ActionListener{
	private Controller _ctrl;
	private boolean _stopped;
	private JButton openFiles, physics, play, stop, off;
	private SpinnerModel jp1;
	private JFrame jf;
	
	public ControlPanel (Controller cc) {
		this._ctrl = cc;
		this._stopped = true;
		initGUI();
		this._ctrl.addObserver(this);
	}
	
	@SuppressWarnings("deprecation")
	private void initGUI() {
		this.setLayout(null);
		jf = new JFrame();
		jf.setLayout(new BorderLayout());
		
		jf.setSize(1200, 1000);
		
		JPanel controlPanel = createPanel(Color.gray, 50, 50);
		JPanel Bodies = createPanel(Color.blue, 100, 100);
		JPanel viewer = createPanel(Color.green, 200, 200);
		JPanel statusBar = createPanel(Color.gray, 40, 40);
		
		this.openFiles = new JButton();
		this.physics = new JButton();
		this.play = new JButton();
		this.stop = new JButton();
		this.off = new JButton();
		JTextField steps = new JTextField("Steps");
		
		//Caja para poner los steps deseados;
		this.jp1 = new SpinnerNumberModel();
		JSpinner jp = new JSpinner(jp1);
		
		this.openFiles.setIcon(new ImageIcon("resources/icons/open.png"));
		this.openFiles.setSize(20, 20);
		
		this.openFiles.setToolTipText("Select file");
		this.openFiles.addActionListener((ActionListener) this);
		
		
		this.physics.setIcon(new ImageIcon("resources/icons/physics.png"));
		this.physics.setSize(20, 20);
		this.physics.setToolTipText("Select instructions");
		this.physics.addActionListener((ActionListener) this);
		
		
		this.play.setIcon(new ImageIcon("resources/icons/run.png"));
		this.play.setSize(100, 100);
		this.play.setToolTipText("Start simulation");
		this.play.addActionListener((ActionListener) this);
		
	
		this.stop.setIcon(new ImageIcon("resources/icons/stop.png"));
		this.stop.setSize(100, 100);
		this.stop.setToolTipText("Stop simulation");
		this.stop.addActionListener((ActionListener) this);
		

		this.off.setIcon(new ImageIcon("resources/icons/exit.png"));
		this.off.setSize(100, 100);
		this.off.setToolTipText("Turn off");
		this.off.addActionListener((ActionListener) this);
		
		controlPanel.add(openFiles);
		controlPanel.add(physics);
		controlPanel.add(play);
		controlPanel.add(stop);
		controlPanel.add(steps);
		controlPanel.add(jp);
		controlPanel.add(off);
		
		jf.add(controlPanel, BorderLayout.PAGE_START);		
		//aniadir los paneles correctamente.
		jf.add(Bodies, BorderLayout.CENTER);
		jf.add(viewer, BorderLayout.AFTER_LINE_ENDS);
		jf.add(statusBar, BorderLayout.PAGE_END);
		jf.show();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private JPanel createPanel(Color color, int x, int y) {
		 JPanel panel;
		 panel = new JPanel();
		 panel.setBackground(color);
		 panel.setPreferredSize(new Dimension(x, y));
		return panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.openFiles) {
			JFileChooser jfile = new JFileChooser("resources/output");
			//Cambios en "save".
			
			jfile.showSaveDialog(null);
			this._ctrl.reset();
			//this._ctrl.loadBodies(jfile.getSelectedFile());
		}
		
		
		
		else if(e.getSource() == this.physics) {
			this.setLayout(null);
			JFrame ops = new JFrame("Gravity Laws Selector");
			JPanel panel = new JPanel();
			JComboBox<String> opsGL = new JComboBox<String>();
			JButton yes = new JButton("Ok");
			JButton no = new JButton("Cancel");
			JLabel mensaje = new JLabel("Select gravity law to be used:\n");
			
			
			opsGL.add(mensaje);
			
			List<JSONObject> aux = this._ctrl.getGravityLawsFactory().getInfo();
			for (JSONObject it : aux) {
				opsGL.addItem(it.getString("desc"));
			}
			
			
			ops.setLayout(new BorderLayout());
			ops.setSize(500, 400);
			panel.setSize(500, 300);
			opsGL.setSize(100, 50);
			panel.add(mensaje, BorderLayout.PAGE_START);
			panel.add(opsGL, BorderLayout.CENTER);
			panel.add(no, BorderLayout.PAGE_END);
			panel.add(yes, BorderLayout.PAGE_END);
			ops.add(panel);
			ops.setLocation(this.jf.getHeight()/2, jf.getWidth()/2);
			ops.show();
		}
		
		else if(e.getSource() == this.play) {
			this._stopped = false;
			this.openFiles.setEnabled(false);
			this.physics.setEnabled(false);
			this.off.setEnabled(false);
			//
			//run_sim((Integer)jp1.getValue());
		}
		
		else if(e.getSource() == this.stop) {
			this._stopped = true;
			this.openFiles.setEnabled(true);
			this.physics.setEnabled(true);
			this.off.setEnabled(true);
			
		}
		else if(e.getSource() == this.off) {
			JFrame confirmar = new JFrame();
			JPanel aux = new JPanel();
			aux.setSize(400, 200);
			JButton yes = new JButton("Si");
			JButton no = new JButton("No");
			JLabel mensaje = new JLabel ("¿Desea salir?");
			confirmar.setSize(400, 100);
			confirmar.setLocation(this.jf.getHeight()/2, jf.getWidth()/2);
			aux.add(yes);
			aux.add(no);
			confirmar.add(mensaje, BorderLayout.PAGE_START);
			confirmar.add(aux, BorderLayout.CENTER);
			confirmar.show(true);
			
			yes.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               System.exit(0);
	            }
	        });
			no.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               confirmar.show(false);
	            }
	        });
		}
	}
	
	// other private/protected methods
	// ...
	public void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
			// TODO show the error in a dialog box
			// TODO enable all buttons
			_stopped = true;
			return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
						run_sim(n-1);
				}
			});
		} 
		else {
			_stopped = true;
			this.openFiles.setEnabled(true);
			this.physics.setEnabled(true);
			this.off.setEnabled(true);
			
			}
		}

	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
