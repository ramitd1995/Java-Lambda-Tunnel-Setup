package Tunnel_setup.Java_Lambda_Tunnel_Setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.testng.IExecutionListener;

public class RunTunnelListener implements IExecutionListener {

	public void onExecutionStart() {

		// downloading tunnel binary
        InputStream input = null;

		String OS = System.getProperty("os.name");

		if (OS.toLowerCase().contains("win")) {
			try {
				input = new URL("https://downloads.lambdatest.com/tunnel/windows/64bit/LT.exe").openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OS.toLowerCase().contains("mac")) {
			try {
				input = new URL("https://downloads.lambdatest.com/tunnel/mac/64bit/LT").openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(OS.toLowerCase().contains("linux")) {
			try {
				input = new URL("https://downloads.lambdatest.com/tunnel/linux/64bit/LT").openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		
		
		try {

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

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

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
