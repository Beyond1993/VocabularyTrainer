package vocabtrainer.rlmodule;
import org.rlcommunity.rlglue.codec.AgentInterface;
import org.rlcommunity.rlglue.codec.util.EnvironmentLoader;
import org.rlcommunity.rlglue.codec.util.AgentLoader;
import org.rlcommunity.rlglue.codec.EnvironmentInterface;


import vocabtrainer.rlmodule.VocabTrainerExperiment;
import vocabtrainer.rlmodule.SarsaAgent;
import vocabtrainer.rlmodule.VocabTrainerExperiment;


/**
*  A simple example of how can you run all components of the samples-mines-sarsa project from a single Java class.
* This is strictly for the convenience of not having to run three terminal windows and start three 
* separate processes all of the time.  In this case, all three components (agent/environment/experiment)
* are communicating over sockets.
*
* You could use this same approach to run any pair of components too, so if only your agent and environment
* were in Java, or only the agent and experiment, you could save some effort by bundling them like this, 
* and run the final missing component from another language.
*
* See RunMinesSarsaExperimentNoSockets to see how you can use the new setGlue method of the RL-Glue Java Extension
* to run Java Agent/Environment/Experiment without sockets in a single program.
*/
public class RunVocabTrainer{
	
	public static void main(String[] args){
		//Create an agentLoader that will start the agent when its run method is called
		AgentLoader theAgentLoader=new AgentLoader(new SarsaAgent());
		//Create an environmentloader that will start the environment when its run method is called
		EnvironmentLoader theEnvironmentLoader=new EnvironmentLoader(new VocabTrainerEnvironment());
		
		//Create threads so that the agent and environment can run asynchronously 		
		Thread agentThread=new Thread(theAgentLoader);
		Thread environmentThread=new Thread(theEnvironmentLoader);
		
		//Start the threads
		agentThread.start();
		environmentThread.start();
		
		//Run the main method of the Sample Experiment, using the arguments were were passed
		//This will run the experiment in the main thread.
		VocabTrainerEnvironment.main(args);
		System.out.println("Vocan Trainer Experiment Complete");
		
		//Quit Java, including stopping the other threads
		System.exit(1);
	}

}