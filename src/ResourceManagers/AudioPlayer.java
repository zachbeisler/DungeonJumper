package ResourceManagers;

import javax.sound.sampled.*;

import Main.Game;

public class AudioPlayer {
	
	private Clip clip;
	private FloatControl gainControl;
	private String type;
	
	public AudioPlayer(String s, String type) {
		
		try {
			
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(
					getClass().getResourceAsStream(
						s
					)
				);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais =
				AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			this.type = type;
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		if(clip == null || Game.options[1] == 0) return;
		stop();
		if(type == "music" && Game.options[2] != 0){
			gainControl.setValue((float)(4.3454*Math.log((float)Game.options[1]/100 * (float)Game.options[2]/100)));
		}else if(type == "sfx" && Game.options[3] != 0){
			gainControl.setValue((float)(4.3454*Math.log((float)Game.options[1]/100 * (float)Game.options[3]/100)));
		}else{
			return;
		}
		System.out.println(gainControl.getValue());
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop(){
		if(!clip.isRunning()){
			play();
		}
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
}














