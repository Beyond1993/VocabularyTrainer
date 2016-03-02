package vocabtrainer.rlmodule;
import org.rlcommunity.rlglue.codec.LocalGlue;
import org.rlcommunity.rlglue.codec.RLGlue;
import org.rlcommunity.rlglue.codec.AgentInterface;
import org.rlcommunity.rlglue.codec.util.EnvironmentLoader;
import org.rlcommunity.rlglue.codec.util.AgentLoader;
import org.rlcommunity.rlglue.codec.EnvironmentInterface;

import vocabtrainer.rlmodule.VocabTrainerExperiment;
import vocabtrainer.rlmodule.VocabTrainerEnvironment;
import vocabtrainer.rlmodule.SarsaAgent;


/**
* A simple example of how can you run all components of the mines-sarsa project from a single Java class
* without using network sockets.  Because we remove the socket overhead, these experiments can execute
* many more steps per second (if they are computationally cheap).
*
* The great thing about this approach is that the experiment, agent, and environment are agnostic
* to how they are being used: locally or over the network.  This means they are still 100% RL-Glue
* portable and can be used together with any other language.
*/
public class RunVocabTrainerNoSockets{
	
	public static void main(String[] args){
		//Create the Agent
		AgentInterface theAgent=new SarsaAgent();
		
		//Create the Environment
		EnvironmentInterface theEnvironment = new VocabTrainerEnvironment();
		
		LocalGlue localGlueImplementation=new LocalGlue(theEnvironment,theAgent);
		RLGlue.setGlue(localGlueImplementation);
		
		
		//Run the main method of the Sample Experiment, using the arguments were were passed
		//This will run the experiment in the main thread.  The Agent and Environment will run
		//locally, without sockets.
		VocabTrainerExperiment.main(args);
		System.out.println("RunVocabTrainerNoSockets Complete");
		
	}

}