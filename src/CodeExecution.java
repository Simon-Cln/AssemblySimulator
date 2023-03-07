import java.util.ArrayList;
import java.util.Scanner;

public class CodeExecution {
    MyStack myStack = new MyStack();

    static int PC = 1;
    static int iteration = 1;
    ArrayList<String> codeLine;
    ALU myALU = new ALU();
    static Register t0 = new Register();
    static Register t1 = new Register();
    static Register t2 = new Register();
    static Register t3 = new Register();

    static boolean stepByStep = false;

    public void executeCode() throws Exception {

        System.out.println("\033[1;37m"+"\nMEMORY:"+"\033[0m");

        for(int j = 1; j <= Memory.memory.get(1).size(); j++){
            System.out.println("\033[0;36m"+Memory.instructionsMap.get(j).get(0) + ": " + Memory.instructionsMap.get(j)+"\033[0m");
        }

        // Initialize the halt (HLT)
        boolean myHLT = false;

        // While we do not get to the last instruction,
        while(!myHLT){

            // Get the line of code at line problemCounter;
            codeLine = Memory.instructionsMap.get(PC);
            System.out.println("\nIteration: "+ iteration +" | PC: "+ PC +" | "+Memory.instructionsMap.get(PC));

            // Done
            if(codeLine.get(0).equalsIgnoreCase("LDA")){
                myALU.LDA(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            // Done
            else if(codeLine.get(0).equalsIgnoreCase("STR")){
                myALU.STR(codeLine.get(1), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            // To be fixed
            else if(codeLine.get(0).equalsIgnoreCase("PUSH")){
                myALU.PUSH(codeLine.get(1));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("POP")){
                myALU.POP(determineRegister(codeLine.get(1)));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            // Done
            else if(codeLine.get(0).equalsIgnoreCase("AND")){
                myALU.AND(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("OR")){
                myALU.OR(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("NOT")){
                myALU.NOT(determineRegister(codeLine.get(1)));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("ADD")){
                myALU.ADD(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("SUB")){
                myALU.SUB(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("DIV")){
                myALU.DIV(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("MUL")){
                myALU.MUL(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("MOD")){
                myALU.MOD(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("INC")){
                myALU.INC(determineRegister(codeLine.get(1)));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
            // Done
            else if(codeLine.get(0).equalsIgnoreCase("DEC")){
                myALU.DEC(determineRegister(codeLine.get(1)));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("BEQ")){
                myALU.BEQ(codeLine.get(1), codeLine.get(2), codeLine.get(3));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("BNE")){
                myALU.BNE(codeLine.get(1), codeLine.get(2), codeLine.get(3));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("BBG")){
                myALU.BBG(codeLine.get(1), codeLine.get(2), codeLine.get(3));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("BSM")){
                myALU.BSM(codeLine.get(1), codeLine.get(2), codeLine.get(3));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("JMP")){
                myALU.JMP(codeLine.get(1));
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("SRL")){
                myALU.SRL(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("SRR")){
                myALU.SRR(determineRegister(codeLine.get(1)), codeLine.get(2));
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }

            else if(codeLine.get(0).equalsIgnoreCase("HLT")){
                myHLT = true;
            }

            // If it is not one of those instructions, then it means we are facing a function (LABEL), or we reached
            // the end of a badly written code.
            // If facing a function, we just need to increment the PC, nothing to do, jumping to LABEL are taken care
            // of by the instructions that needs it.
            else {
                ++PC;
                ++iteration;
                if(stepByStep){
                    enterInput();
                }
            }
        }
    }


    public static Register determineRegister(String registerName){
        if(registerName.equalsIgnoreCase("t0")){
            return t0;
        } else if (registerName.equalsIgnoreCase("t1")){
            return t1;
        } else if (registerName.equalsIgnoreCase("t2")){
            return t2;
        } else {
            return t3;
        }
    }

    private void enterInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to display the : " + "\033[0;36m" + "registers "+"\033[0m"+ "," + "\033[0;32m" + " variables " + "\033[0m" + "and" + "\033[0;33m" + " stack " + "\033[0m" + "content? (Y/N)");
        String answer = sc.nextLine();
        if(answer.equalsIgnoreCase("Y")){
            System.out.println("\033[1;37m"+"\nREGISTERS:"+"\033[0m");
            System.out.println("\033[0;36m"+"t0: " + t0.getDecimalValue());
            System.out.println("t1: " + t1.getDecimalValue());
            System.out.println("t2: " + t2.getDecimalValue());
            System.out.println("t3: " + t3.getDecimalValue()+"\033[0m");


            System.out.println("\033[1;37m"+"\n\nVARIABLES :" + "\033[0m" + "\033[0;32m" + Memory.memory.get(0) + "\033[0m");
            System.out.println("So we have " + Memory.memory.get(0).size() + " variables who are:");
            for(int i = 1; i <= Memory.memory.get(0).size(); i++){
                System.out.println("\033[0;32m"+Memory.dataMap.get(i).get(0) +" : "+ Memory.dataMap.get(i).get(1)+ "\033[0m");
            }


            System.out.println("\033[1;37m"+"\nSTACK: \n"+"\033[0m" + "\033[0;33m" + myStack + "\033[0m");
        }
        System.out.println("Press any keys and enter to continue");
        try {
            char input = (char) System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
