package src.main.java.com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeechified {
	
	private Synthesizer synthesizer;	
	String inputFile = "speechReadyText.txt";
	
	public TextToSpeechified() throws Exception {
		
		try {
			// Set property as Kevin Dictionary
	        System.setProperty(
	            "freetts.voices",
	            "com.sun.speech.freetts.en.us"
	                + ".cmu_us_kal.KevinVoiceDirectory");
	
	        // Register Engine
	        Central.registerEngineCentral(
	            "com.sun.speech.freetts"
	            + ".jsapi.FreeTTSEngineCentral");
	        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
	
	        // Create a Synthesizer        
	        synthesizer = Central.createSynthesizer( new SynthesizerModeDesc( Locale.US ) );
	
	        // Allocate synthesizer
	        synthesizer.allocate();
	
	        // Resume Synthesizer
	        synthesizer.resume();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
    // Deallocate the Synthesizer.
	public void deallocate() throws Exception {
		
		try {
			synthesizer.deallocate();
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	public void speak() throws Exception {
		try {
			// Speaks the given text
	        // until the queue is empty.
			BufferedReader br = new BufferedReader( new FileReader( inputFile ) );
			String line;
			while( ( line = br.readLine() ) != null ) {
				synthesizer.speakPlainText(line, null);
			}
	        synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		try {
			
//			if( args.length < 1 ) {
//				System.out.println("usage: textToSpeechify <filename>");
//				System.exit(0);
//			}
			TextToSpeechified tts_client = new TextToSpeechified();
//			String inputFile = args[0];
//			tts_client.speak( inputFile );
			tts_client.speak( );
			tts_client.deallocate();
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
}