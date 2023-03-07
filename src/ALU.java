import java.util.Map;
import java.util.ArrayList;

public class ALU {

    public boolean isRegister(String s) {
        if (s.equalsIgnoreCase("t0") || s.equalsIgnoreCase("t1") || s.equalsIgnoreCase("t2") || s.equalsIgnoreCase("t3")) {
            return true;
        }
        return false;
    }

    public boolean isVariable(String s) {
        if (s.charAt(0) >= 'a' && s.charAt(0) <= 'z' || s.charAt(0) >= 'A' && s.charAt(0) <= 'Z') {
            return true;
        }
        return false;
    }

    public Register LDA(Register registerName, String valueName) throws Exception {
        if (isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            // If there is indirect addressing (var+2...)
            if(checkIndirectAddress(valueName)){
                // This is the 2 of var+2, or 5 of var+5
                int plusAddressValue = getIndirectAddressValue(valueName);
                int index = determineVariableIndex(valueName)+plusAddressValue;
                if(index>Memory.dataMap.size()){
                    registerName.setRegister(0);
                } else {
                    int value = Integer.parseInt(Memory.dataMap.get(index).get(1));
                    registerName.setRegister(value);
                }
            } else {
                int index = determineVariableIndex(valueName);
                int value = Integer.parseInt(Memory.dataMap.get(index).get(1));
                registerName.setRegister(value);
            }
        } else {
            int value = Integer.parseInt(valueName);
            registerName.setRegister(value);
        }
        return registerName;
    }

    public void STR(String variableName, String valueName){
        int strValue;
        if(checkIndirectAddress(variableName)){
            int plusAddressValue = getIndirectAddressValue(variableName);
            int index = determineVariableIndex(variableName)+plusAddressValue;
            // If index is greater than dataMap size, then variable doesn't exist, so we add this value
            if(index>Memory.dataMap.size()){
                strValue = 0;
                Memory.dataMap.get(index).set(index, String.valueOf(strValue));
            } else {
                if (isRegister(valueName)) {
                    int integerValue = CodeExecution.determineRegister(valueName).getDecimalValue();
                    Memory.dataMap.get(index).set(1, String.valueOf(integerValue));
                } else {
                    Memory.dataMap.get(index).set(1, valueName);
                }
            }
        } else {
            if (isRegister(valueName)) {
                int integerValue = CodeExecution.determineRegister(valueName).getDecimalValue();
                //Memory.dataMap.put(variableName, integerValue);
                int index = determineVariableIndex(variableName);
                Memory.dataMap.get(index).set(1, String.valueOf(integerValue));
            } else {
                //Memory.dataMap.put(variableName, Integer.parseInt(valueName));
                int index = determineVariableIndex(variableName);
                Memory.dataMap.get(index).set(1, valueName);
            }
        }
    }

    public void PUSH(String valueName){
        if(isRegister(valueName)){
            int integerValue = CodeExecution.determineRegister(valueName).getDecimalValue();
            MyStack.push(integerValue);
        } else if(isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int integerValue = Integer.parseInt(Memory.dataMap.get(index).get(1));
            MyStack.push(integerValue);
        } else {
            int integerValue = Integer.parseInt(valueName);
            MyStack.push(integerValue);
        }
    }

    public void POP(Register register) {
        int integerValue = MyStack.pop();
        register.setRegister(integerValue);
    }



    public void AND(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() & registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) & registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) & registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void OR(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() | registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            // char [] binary = int2binary(Memory.dataMap.get(valueName) | registerName.getDecimalValue());
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) | registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) | registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void NOT(Register registerName) {
        int value = ~registerName.getDecimalValue();
        registerName.setRegister(value);

    }

    public void ADD(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() + registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) + registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) + registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }


    public void SUB(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() - registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) - registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) - registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void DIV(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() / registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) / registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) / registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void MUL(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() * registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) * registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) * registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void MOD(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() % registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) % registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) % registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void SRL(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() >> registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) >> registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) >> registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }

    public void SRR(Register registerName, String valueName) {
        if(isRegister(valueName)) {
            int value = CodeExecution.determineRegister(valueName).getDecimalValue() << registerName.getDecimalValue();
            registerName.setRegister(value);
        } else if (isVariable(valueName)) {
            int index = determineVariableIndex(valueName);
            int value = Integer.parseInt(Memory.dataMap.get(index).get(1)) << registerName.getDecimalValue();
            registerName.setRegister(value);
        } else {
            int value = Integer.parseInt(valueName) << registerName.getDecimalValue();
            registerName.setRegister(value);
        }
    }


   public void INC(Register registerName) {
       int value = registerName.getDecimalValue() + 1;
       registerName.setRegister(value);
   }

   public void DEC (Register registerName) throws Exception {
        int value = registerName.getDecimalValue() - 1;
        registerName.setRegister(value);
   }


   public void BEQ(String firstParam, String secondParam, String LABEL) {
       int firstValue;
       int secondValue;
       // If 1st param is a register
       if (isRegister(firstParam)) {
           firstValue = CodeExecution.determineRegister(firstParam).getDecimalValue();
           // If 2nd param is also a register
           if (isRegister(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue == secondValue) {
                   JMP(LABEL);
               }
           }
           // If 2nd param is a variable
           else if (isVariable(secondParam)) {
               secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
               if (firstValue == secondValue) {
                   JMP(LABEL);
               }
           }
           // Then this means that 2nd param is for sure a constant
           else if (firstValue == Integer.parseInt(secondParam)) {
               JMP(LABEL);
           }
       }// If 1st param is a variable
       else if (isVariable(firstParam)) {
           firstValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(firstParam)).get(1));
           // If 2nd param is a register
           if (isRegister(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue == secondValue) {
                   JMP(LABEL);
               }
           }
           // If 2nd param is also a variable
           else if (isVariable(secondParam)) {
               secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
               if (firstValue == secondValue) {
                   JMP(LABEL);
               }
           }
           // 2nd param is for sure a constant
           else if (firstValue == Integer.parseInt(secondParam)) {
               JMP(LABEL);
           }
       }
       // 1st param is a constant
       else {
           firstValue = Integer.parseInt(firstParam);
           // 2nd param is a register
           if (isRegister(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue == secondValue) {
                   JMP(LABEL);
               }
           }
           // 2nd param is a variable
           else if (isVariable(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue == secondValue) {
                   JMP(LABEL);
               }
           }
           // 2nd param is forcibly also a constant
           else if (firstValue == Integer.parseInt(secondParam)) {
               JMP(LABEL);
           }
       }
   }

   public void BNE(String firstParam, String secondParam, String LABEL) {
       int firstValue;
       int secondValue;
       // If 1st param is a register
       if (isRegister(firstParam)) {
           firstValue = CodeExecution.determineRegister(firstParam).getDecimalValue();
           // If 2nd param is also a register
           if (isRegister(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue != secondValue) {
                   JMP(LABEL);
               }
           }
           // If 2nd param is a variable
           else if (isVariable(secondParam)) {
               secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
               if (firstValue != secondValue) {
                   JMP(LABEL);
               }
           }
           // Then this means that 2nd param is for sure a constant
           else if (firstValue != Integer.parseInt(secondParam)) {
               JMP(LABEL);
           }
       }
       // If 1st param is a variable
       else if (isVariable(firstParam)) {
           firstValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(firstParam)).get(1));
           // If 2nd param is a register
           if (isRegister(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue != secondValue) {
                   JMP(LABEL);
               }
           }
           // If 2nd param is also a variable
           else if (isVariable(secondParam)) {
               secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
               if (firstValue != secondValue) {
                   JMP(LABEL);
               }
           }
           // 2nd param is for sure a constant
           else if (firstValue != Integer.parseInt(secondParam)) {
               JMP(LABEL);
           }
       }
       // 1st param is a constant
       else {
           firstValue = Integer.parseInt(firstParam);
           // 2nd param is a register
           if (isRegister(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue != secondValue) {
                   JMP(LABEL);
               }
           }
           // 2nd param is a variable
           else if (isVariable(secondParam)) {
               secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
               if (firstValue != secondValue) {
                   JMP(LABEL);
               }
           }
           // 2nd param is forcibly also a constant
           else if (firstValue != Integer.parseInt(secondParam)) {
               JMP(LABEL);
           }
       }
   }

    public void BBG(String firstParam, String secondParam, String LABEL){
        int firstValue;
        int secondValue;
        // If 1st param is a register
        if(isRegister(firstParam)) {
            firstValue = CodeExecution.determineRegister(firstParam).getDecimalValue();
            // If 2nd param is also a register
            if(isRegister(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue > secondValue){
                    JMP(LABEL);
                }
            }
            // If 2nd param is a variable
            else if (isVariable(secondParam)){
                secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
                if(firstValue > secondValue){
                    JMP(LABEL);
                }
            }
            // Then this means that 2nd param is for sure a constant
            else if (firstValue > Integer.parseInt(secondParam)){
                JMP(LABEL);
            }
        }
        // If 1st param is a variable
        else if (isVariable(firstParam)) {
            firstValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(firstParam)).get(1));
            // If 2nd param is a register
            if(isRegister(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue > secondValue){
                    JMP(LABEL);
                }
            }
            // If 2nd param is also a variable
            else if (isVariable(secondParam)){
                secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
                if(firstValue > secondValue){
                    JMP(LABEL);
                }
            }
            // 2nd param is for sure a constant
            else if (firstValue > Integer.parseInt(secondParam)){
                JMP(LABEL);
            }
        }
        // 1st param is a constant
        else {
            firstValue = Integer.parseInt(firstParam);
            // 2nd param is a register
            if(isRegister(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue > secondValue){
                    JMP(LABEL);
                }
            }
            // 2nd param is a variable
            else if(isVariable(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue > secondValue){
                    JMP(LABEL);
                }
            }
            // 2nd param is forcibly also a constant
            else if(firstValue > Integer.parseInt(secondParam)){
                JMP(LABEL);
            }
        }
    }

    public void BSM(String firstParam, String secondParam, String LABEL){
        int firstValue;
        int secondValue;
        // If 1st param is a register
        if(isRegister(firstParam)) {
            firstValue = CodeExecution.determineRegister(firstParam).getDecimalValue();
            // If 2nd param is also a register
            if(isRegister(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue < secondValue){
                    JMP(LABEL);
                }
            }
            // If 2nd param is a variable
            else if (isVariable(secondParam)){
                secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
                if(firstValue < secondValue){
                    JMP(LABEL);
                }
            }
            // Then this means that 2nd param is for sure a constant
            else if (firstValue < Integer.parseInt(secondParam)){
                JMP(LABEL);
            }
        }
        // If 1st param is a variable
        else if (isVariable(firstParam)) {
            firstValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(firstParam)).get(1));
            // If 2nd param is a register
            if(isRegister(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue < secondValue){
                    JMP(LABEL);
                }
            }
            // If 2nd param is also a variable
            else if (isVariable(secondParam)){
                secondValue = Integer.parseInt(Memory.dataMap.get(determineVariableIndex(secondParam)).get(1));
                if(firstValue < secondValue){
                    JMP(LABEL);
                }
            }
            // 2nd param is for sure a constant
            else if (firstValue < Integer.parseInt(secondParam)){
                JMP(LABEL);
            }
        }
        // 1st param is a constant
        else {
            firstValue = Integer.parseInt(firstParam);
            // 2nd param is a register
            if(isRegister(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue < secondValue){
                    JMP(LABEL);
                }
            }
            // 2nd param is a variable
            else if(isVariable(secondParam)){
                secondValue = CodeExecution.determineRegister(secondParam).getDecimalValue();
                if(firstValue < secondValue){
                    JMP(LABEL);
                }
            }
            // 2nd param is forcibly also a constant
            else if(firstValue < Integer.parseInt(secondParam)){
                JMP(LABEL);
            }
        }
    }

    public void JMP(String label){
        for(Map.Entry<Integer, ArrayList<String>> set : Memory.instructionsMap.entrySet()){
            if(set.getValue().get(0).equals(label)){
                CodeExecution.PC = set.getKey();
                break;
            }
        }
    }

    // Return the index of the array for which the variable corresponds to variableName
    public int determineVariableIndex(String variableName){
        StringBuilder normalVarName = new StringBuilder("");
        if(variableName.contains("+")){
            int plusPosition = 0;
            for(int i=0; i<variableName.length(); i++){
                if(variableName.charAt(i) == '+'){
                    plusPosition = i;
                    break;
                }
            }
            for(int i=0; i<plusPosition; i++){
                normalVarName.append(variableName.charAt(i));
            }
            for(Map.Entry<Integer, ArrayList<String>> entry : Memory.dataMap.entrySet()){
                if(entry.getValue().get(0).equals(normalVarName.toString())){
                    return entry.getKey();
                }
            }
        } else {
            for(Map.Entry<Integer, ArrayList<String>> entry : Memory.dataMap.entrySet()){
                if(entry.getValue().get(0).equals(variableName)){
                    return entry.getKey();
                }
            }
        }
        return 0;
    }

    public boolean checkIndirectAddress(String variableName){
        return variableName.contains("+");
    }

    // Gets the value of the plusAddress
    public int getIndirectAddressValue(String variableName){
        int plusPosition = 0;
        for(int i=0; i<variableName.length(); i++){
            if(variableName.charAt(i) == '+'){
                plusPosition = i;
                break;
            }
        }
        StringBuilder value = new StringBuilder("");
        for(int i=plusPosition+1; i<variableName.length(); i++){
            value.append(variableName.charAt(i));
        }
        return Integer.parseInt(value.toString());
    }
}
