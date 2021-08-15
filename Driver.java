public class Driver
{
    public static void main(String args[])
    {
        /*
        Motor sumpAuger1 = new Motor("sumpAuger1");
        Motor sumpAuger2 = new Motor("sumpAuger2");
        Motor legJump1 = new Motor("legJump1", sumpAuger1, sumpAuger2);
        Motor legJump2 = new Motor("legJump2", sumpAuger1, sumpAuger2);
        Motor leg1 = new Motor("leg1", legJump1);
        Motor leg2 = new Motor("leg2", legJump2);

        GrainInput sump1 = new GrainInput("sump1", sumpAuger1);
        GrainOutput bin1 = new GrainOutput("bin1", leg1);
        GrainOutput bin2 = new GrainOutput("bin2", leg1);
        GrainOutput bin3 = new GrainOutput("bin3", leg2);

        View.systemStatus();
        View.grainPath(sump1, bin1);
        View.grainPath(sump1, bin3);

        SystemControl.startSystem(sump1, bin1);
        View.systemStatus();
        legJump1.eStop();
        View.systemStatus();
        */

        Motor a1 = new Motor("a1");
        Motor b1 = new Motor("b1", a1);
        Motor b2 = new Motor("b2", a1);
        Motor c1 = new Motor("c1", b1, b2);

        GrainInput in1 = new GrainInput("in1", a1);
        GrainOutput out1 = new GrainOutput("out1", c1);

        View.grainPath(in1, out1);
        View.systemStatus();
        SystemControl.startSystem(in1, out1);
        View.systemStatus();
    }    
}
