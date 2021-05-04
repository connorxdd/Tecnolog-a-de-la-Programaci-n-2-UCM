package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel
implements SimulatorObserver {
	// ...
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {
		this._currLaws = new JLabel("Law: Falling to center");
		this._currTime = new JLabel("Time: 0");
		this._numOfBodies = new JLabel("Bodies: 0");
		ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		implementElements();
	}


	private void implementElements() {
		this.add(this._currTime);
		this.add(this._numOfBodies);
		this.add(this._currLaws);
	}
	
	
	

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		if(!bodies.isEmpty()) {
			this._numOfBodies.setText("Bodies: " + Integer.toString(bodies.size()));
		}		
		this._currTime.setText("Time: " + Double.toString(dt));
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		this._numOfBodies.setText("Bodies: " + Integer.toString(bodies.size()));
		this._currTime.setText("Time: " + Double.toString(0));
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		this._numOfBodies.setText("Bodies: " + Integer.toString(bodies.size()));
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		Double aux = Double.parseDouble(this._currTime.getText().substring(6));
		aux += time;
		this._currTime.setText("Time: " + Double.toString(aux));
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		this._currLaws.setText("Laws " + gLawsDesc);
	}
}
