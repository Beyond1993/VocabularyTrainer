package vocabtrainer.rlmodule;
import java.util.Random;
import org.rlcommunity.rlglue.codec.EnvironmentInterface;
import org.rlcommunity.rlglue.codec.types.Action;
import org.rlcommunity.rlglue.codec.types.Observation;
import org.rlcommunity.rlglue.codec.types.Reward_observation_terminal;
import org.rlcommunity.rlglue.codec.util.EnvironmentLoader;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpecVRLGLUE3;
import org.rlcommunity.rlglue.codec.taskspec.TaskSpec;
import org.rlcommunity.rlglue.codec.taskspec.ranges.IntRange;
import org.rlcommunity.rlglue.codec.taskspec.ranges.DoubleRange;

import vocabtrainer.interactionmodule.FlashCardManager;

public class VocabTrainerEnvironment implements EnvironmentInterface {


    //WorldDescription contains the state of the world and manages the dynamics.
    FlashCardManager flashCardManager;
    //These are used if the environment has been sent a message to use a fixed
    //starting state.
    // boolean fixedStartState = false;
    public String env_init() {
        //This is hard coded, but there is no reason it couldn't be automatically
        //generated or read from a file.



        flashCardManager = new FlashCardManager();


        //Create a task spec programmatically.  This task spec encodes that state, action, and reward space for the problem.
        //You could forgo the task spec if your agent and environment have been created specifically to work with each other
        //ie, there is no need to share this information at run time.  You could also use your own ad-hoc task specification language,
        //or use the official one but just hard code the string instead of constructing it this way.
        TaskSpecVRLGLUE3 theTaskSpecObject = new TaskSpecVRLGLUE3();
        theTaskSpecObject.setEpisodic();
        theTaskSpecObject.setDiscountFactor(0.5d);
        //Specify that there will be an integer observation [0,1000] for the state
        theTaskSpecObject.addDiscreteObservation(new IntRange(0, flashCardManager.getNumStates() - 1));
        //Specify that there will be an integer action [0,99]
        theTaskSpecObject.addDiscreteAction(new IntRange(0, 99));
        //Specify the reward range [-3.0,5.0]
        theTaskSpecObject.setRewardRange(new DoubleRange(-3.0, 5.0));
        theTaskSpecObject.setExtra("RL Vocabular Trainer by Raghu");
        String taskSpecString = theTaskSpecObject.toTaskSpec();
        TaskSpec.checkTaskSpec(taskSpecString);

        return taskSpecString;
    }

    /**
     * Put the environment in a random state and return the appropriate observation.
     * @return
     */
    public Observation env_start() {
        System.out.println("Entering env_start");
        flashCardManager.setNewLearnerState();
        Observation theObservation = new Observation(1, 0, 0);
        System.out.println("Initial State is : " + flashCardManager.getState());
        theObservation.setInt(0, flashCardManager.getState());
        return theObservation;
    }

    /**
     * Make sure the action is in the appropriate range, update the state,
     * generate the new observation, reward, and whether the episode is over.
     * @param thisAction
     * @return
     */
    public Reward_observation_terminal env_step(Action thisAction) {
        /* Make sure the action is valid */
        System.out.println("Entering env_step");

        assert (thisAction.getNumInts() == 1) : "Expecting a 1-dimensional integer action. " + thisAction.getNumInts() + "D was provided";
        assert (thisAction.getInt(0) >= 0) : "Action should be in [0,99], " + thisAction.getInt(0) + " was provided";
        assert (thisAction.getInt(0) < 100) : "Action should be in [0,99], " + thisAction.getInt(0) + " was provided";
        

        System.out.println("Agent has suggest question : " + thisAction.getInt(0));
        flashCardManager.setAgentAction(thisAction.getInt(0));


        Observation theObservation = new Observation(1, 0, 0);
        System.out.println("Step State is : " + flashCardManager.getState());
        theObservation.setInt(0, flashCardManager.getState());
        Reward_observation_terminal RewardObs = new Reward_observation_terminal();
        RewardObs.setObservation(theObservation);
        RewardObs.setTerminal(flashCardManager.isTerminal());
        System.out.println("Step Reward is : " + flashCardManager.getReward());
        RewardObs.setReward(flashCardManager.getReward());

        return RewardObs;
    }

    public void env_cleanup() {
    }

    public String env_message(String message) {
        
		if (message.startsWith("print-state")){
			// flashCardManager.print_state();
			return "Message understood.  Printed the state.";
        }

        return "VocabTrainerEnvironment(Java) does not understand your message.";
    }

    /**
     * This is a trick we can use to make the agent easily loadable.
     * @param args
     */
    public static void main(String[] args) {
        EnvironmentLoader theLoader = new EnvironmentLoader(new VocabTrainerEnvironment());
        theLoader.run();
    }
}

