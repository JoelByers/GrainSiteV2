import java.util.LinkedList;
import java.util.List;

public class SystemControl
{
    private static List<Motor> motorSearch = new LinkedList<Motor>();

    public static void startSystem(GrainInput grainIn, GrainOutput grainOut)
    {

        for(int i = 0; i < grainIn.getMotorOutputs().size(); i++)
        {
            findMotorPath(grainOut.getMotorInput(), grainIn.getMotorOutputs().get(i));
        }

        for(int i = 0; i < motorSearch.size(); i++)
        {
            motorSearch.get(i).start();
        }

        motorSearch.clear();
    }

    private static boolean findMotorPath(Motor thisMotor, Motor source)
    {
        // Go fron output to input

        motorSearch.add(thisMotor);

        if(thisMotor == source)
        {
            return true;
        }
        else if(thisMotor.getInputs().size() == 0)
        {
            motorSearch.remove(thisMotor);
        }
        else
        {
            for(int i = 0; i < thisMotor.getInputs().size(); i++)
            {
                if(findMotorPath(thisMotor.getInputs().get(i), source))
                {
                    return true;
                }
            }

            motorSearch.remove(thisMotor);
        }

        return false;
    }

    public static void stopSystem(GrainInput grainIn, GrainOutput grainOut)
    {
        for(int i = 0; i < grainIn.getMotorOutputs().size(); i++)
        {
            findMotorPath(grainOut.getMotorInput(), grainIn.getMotorOutputs().get(i));
        }

        for(int i = 0; i < motorSearch.size(); i++)  //  Check every motor marked for shutdown
        {
            boolean keepRunning = false;

            for(int j = 0; j < motorSearch.get(i).getOutputs().size(); j++)
            {
                if(motorSearch.get(i).getOutputs().get(j).getStatus() == MotorStatus.RUN)
                {
                    keepRunning = true;
                }
            }

            if(!keepRunning)
            {
                motorSearch.get(i).cleanStop();
            }
        }

        motorSearch.clear();
    }

    public static void eStopSystem(Motor faultMotor)
    {
        // First hard stop all motors before fault
        for(int i = 0; i < GrainInput.getSystemGrainInputs().size(); i++)
        {
            for(int j = 0; j < GrainInput.getSystemGrainInputs().get(i).getMotorOutputs().size(); j++)
            {
                findMotorPath(faultMotor, GrainInput.getSystemGrainInputs().get(i).getMotorOutputs().get(j));
            }
        }

        for(int k = 0; k < motorSearch.size(); k++)
        {
            if(motorSearch.get(k) != faultMotor && motorSearch.get(k).getStatus() == MotorStatus.RUN)
            {
                motorSearch.get(k).hardStop();
            }
        }
        
        motorSearch.clear();

        // Clean Stop all motors after fault

        for(int i = 0; i < GrainOutput.getSystemGrainOutputs().size(); i++)
        {
            findMotorPath(GrainOutput.getSystemGrainOutputs().get(i).getMotorInput(), faultMotor);
        }

        for(int k = 0; k < motorSearch.size(); k++)
        {
            if(motorSearch.get(k) != faultMotor && motorSearch.get(k).getStatus() == MotorStatus.RUN)
            {
                motorSearch.get(k).cleanStop();
            }
        }
        
        motorSearch.clear();
    }
}
