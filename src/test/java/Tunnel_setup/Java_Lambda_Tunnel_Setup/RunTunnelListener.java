package Tunnel_setup.Java_Lambda_Tunnel_Setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.testng.IExecutionListener;

public class RunTunnelListener implements IExecutionListener {

		public void onExecutionStart() {
			
			// downloading tunnel binary
			InputStream input;
			try {
				 input = new URL("https://downloads.lambdatest.com/tunnel/windows/64bit/LT.exe").openStream();
				 Files.copy(input, Paths.get("LT.exe"), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			// configuring tunnel
			Thread runTunnel = new Thread(() -> {
			
			
			Runtime rt = Runtime.getRuntime();
			String commands = "LT  -user ramitd@lambdatest.com -key Ar7sr82ACbQXRi23ujktWaSuBRq9jOjInvBaelieyC00XavZUP";
			Process proc = null;
			try {
				proc = rt.exec(commands);
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
			     InputStreamReader(proc.getErrorStream()));

			// Read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			String s = null;
			try {
				while ((s = stdInput.readLine()) != null) {
				    System.out.println(s);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		
			// Read any errors from the attempted command
			System.out.println("Here is the standard error of the command (if any):\n");
			try {
				while ((s = stdError.readLine()) != null) {
				    System.out.println(s);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			});
			
			runTunnel.setDaemon(true);
			runTunnel.start();
			
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

		
		public void onExecutionFinish() {
			
			System.out.println("Tunnel is still active, Ending the current test session");
		}
		
		

	}



