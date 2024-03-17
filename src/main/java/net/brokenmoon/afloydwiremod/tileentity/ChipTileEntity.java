package net.brokenmoon.afloydwiremod.tileentity;

import com.mojang.nbt.CompoundTag;
import net.brokenmoon.afloydwiremod.api.AbstractWireTileEntity;
import net.brokenmoon.afloydwiremod.gui.WiringButton;
import net.brokenmoon.afloydwiremod.packet.WiremodPacketSyncIO;
import net.minecraft.core.net.packet.Packet;

public class ChipTileEntity extends AbstractWireTileEntity {
    public String mode = "none";
    private boolean shouldIncrement = true;
    private boolean shouldDecrement = true;
    private int timer;
    public int tickAmount = 0;

    @Override
    public void tick(){
        if(timer == 0){
            switch (mode){
                case "Pulse":
                    outputs[0].floatvalue = 0;
                    outputs[0].stringvalue = "";
                    updateIO();
            }
        } else{
            timer--;
        }
    }

    @Override
    public void update() {
        switch (mode) {
            case "inc":
                doIncrement();
                updateIO();
                break;
            case "dec":
                doDecrement();
                updateIO();
                break;
            case "incdec":
                doIncrementDecrement();
                updateIO();
                break;
            case "mem":
                doMemory();
                updateIO();
                break;
            case "dup":
                doDup();
                updateIO();
                break;
            case "add":
                doAdd();
                updateIO();
                break;
            case "sub":
                doSub();
                updateIO();
                break;
            case "mult":
                doMult();
                updateIO();
                break;
            case "div":
                doDiv();
                updateIO();
                break;
            case "NOT":
                doNOT();
                updateIO();
                break;
            case "AND":
                doAND();
                updateIO();
                break;
            case "OR":
                doOR();
                updateIO();
                break;
            case "XOR":
                doXOR();
                updateIO();
                break;
            case "NAND":
                doNAND();
                updateIO();
                break;
            case "NOR":
                doNOR();
                updateIO();
                break;
            case "XNOR":
                doXNOR();
                updateIO();
                break;
            case "TRUE":
                outputs[0].floatvalue = 1.0f;
                updateIO();
                break;
            case "FALSE":
                outputs[0].floatvalue = 0.0f;
                updateIO();
                break;
            case "==":
                doEquals();
                updateIO();
                break;
            case "!=":
                doNotEquals();
                updateIO();
                break;
            case ">":
                doGreater();
                updateIO();
                break;
            case ">=":
                doGreaterEq();
                updateIO();
                break;
            case "<":
                doLess();
                updateIO();
                break;
            case "<=":
                doLessEq();
                updateIO();
                break;
            case "<=>":
                doSpaceShip();
                updateIO();
                break;
            case "Pulse":
                doPulse();
                break;
        }
        worldObj.markBlockNeedsUpdate(x, y, z);
    }

    private void doPulse() {
        if(inputs[0].floatvalue != 0 && timer == 0) {
            outputs[0].floatvalue = inputs[0].floatvalue;
            outputs[0].stringvalue = inputs[0].stringvalue;
            updateIO();
            if(tickAmount == 0){
                outputs[0].floatvalue = 0;
                outputs[0].stringvalue = "";
                updateIO();
            } else{
                timer = tickAmount - 1;
            }
        }
    }

    private void doSpaceShip() {
        outputs[0].floatvalue = Float.compare(inputs[0].floatvalue, inputs[1].floatvalue);
    }

    private void doLessEq() {
        outputs[0].floatvalue = inputs[0].floatvalue <= inputs[1].floatvalue ? 1.0f : 0.0f;
    }

    private void doLess() {
        outputs[0].floatvalue = inputs[0].floatvalue < inputs[1].floatvalue ? 1.0f : 0.0f;
    }

    private void doGreaterEq() {
        outputs[0].floatvalue = inputs[0].floatvalue >= inputs[1].floatvalue ? 1.0f : 0.0f;
    }

    private void doGreater() {
        outputs[0].floatvalue = inputs[0].floatvalue > inputs[1].floatvalue ? 1.0f : 0.0f;
    }

    private void doNotEquals() {
        outputs[0].floatvalue = inputs[0].floatvalue != inputs[1].floatvalue ? 1.0f : 0.0f;
    }

    private void doEquals() {
        outputs[0].floatvalue = inputs[0].floatvalue == inputs[1].floatvalue ? 1.0f : 0.0f;
    }

    private void doXNOR() {
        outputs[0].floatvalue = inputs[0].floatvalue != 0 ^ inputs[1].floatvalue != 0 ? 0.0f : 1.0f;
    }

    private void doNOR() {
        outputs[0].floatvalue = inputs[0].floatvalue != 0 || inputs[1].floatvalue != 0 ? 0.0f : 1.0f;
    }

    private void doNAND() {
        outputs[0].floatvalue = inputs[0].floatvalue != 0 && inputs[1].floatvalue != 0 ? 0.0f : 1.0f;
    }

    private void doXOR() {
        outputs[0].floatvalue = inputs[0].floatvalue != 0 ^ inputs[1].floatvalue != 0 ? 1.0f : 0.0f;
    }

    private void doOR() {
        outputs[0].floatvalue = inputs[0].floatvalue != 0 || inputs[1].floatvalue != 0 ? 1.0f : 0.0f;
    }

    private void doAND() {
        outputs[0].floatvalue = inputs[0].floatvalue != 0 && inputs[1].floatvalue != 0 ? 1.0f : 0.0f;
    }

    private void doNOT() {
        this.outputs[0].floatvalue = inputs[0].floatvalue != 0 ? 0.0f : 1.0f;
    }

    private void doIncrementDecrement() {
        if (inputs[3].floatvalue > 0) {
            this.outputs[0].floatvalue = 0;
            return;
        }
        if (this.inputs[1].floatvalue > 0 && shouldIncrement) {
            this.outputs[0].floatvalue = this.outputs[0].floatvalue + this.inputs[0].floatvalue;
            shouldIncrement = false;
        } else if (this.inputs[1].floatvalue == 0.0 && !shouldIncrement) {
            shouldIncrement = true;
        }
        if (this.inputs[2].floatvalue > 0 && shouldDecrement) {
            this.outputs[0].floatvalue = this.outputs[0].floatvalue - this.inputs[0].floatvalue;
            shouldDecrement = false;
        } else if (this.inputs[1].floatvalue == 0.0 && !shouldDecrement) {
            shouldDecrement = true;
        }
    }

    private void doDecrement() {
        if (inputs[2].floatvalue > 0) {
            this.outputs[0].floatvalue = 0;
            return;
        }
        if (this.inputs[1].floatvalue > 0 && shouldIncrement) {
            this.outputs[0].floatvalue = this.outputs[0].floatvalue - this.inputs[0].floatvalue;
            shouldIncrement = false;
        } else if (this.inputs[1].floatvalue == 0.0 && !shouldIncrement) {
            shouldIncrement = true;
        }
    }

    private void doDiv() {
        outputs[0].floatvalue = inputs[0].floatvalue / inputs[1].floatvalue;
    }

    private void doMult() {
        outputs[0].floatvalue = inputs[0].floatvalue * inputs[1].floatvalue;
    }

    private void doSub() {
        outputs[0].floatvalue = inputs[0].floatvalue - inputs[1].floatvalue;
    }

    private void doDup() {
        outputs[0].floatvalue = inputs[0].floatvalue;
        outputs[1].floatvalue = inputs[0].floatvalue;
        outputs[0].stringvalue = inputs[0].stringvalue;
        outputs[1].stringvalue = inputs[0].stringvalue;
    }

    private void doAdd() {
        outputs[0].floatvalue = inputs[0].floatvalue + inputs[1].floatvalue;
        outputs[0].stringvalue = inputs[0].stringvalue + inputs[1].stringvalue;
    }

    public void doIncrement() {
        if (inputs[2].floatvalue > 0) {
            this.outputs[0].floatvalue = 0;
            return;
        }
        if (this.inputs[1].floatvalue > 0 && shouldIncrement) {
            this.outputs[0].floatvalue = this.outputs[0].floatvalue + this.inputs[0].floatvalue;
            shouldIncrement = false;
        } else if (this.inputs[1].floatvalue == 0.0 && !shouldIncrement) {
            shouldIncrement = true;
        }
    }

    private void doMemory() {
        if (inputs[2].floatvalue > 0) {
            this.outputs[0].floatvalue = 0;
            return;
        }
        if (this.inputs[1].floatvalue > 0 && shouldIncrement) {
            this.outputs[0].floatvalue = this.inputs[0].floatvalue;
            shouldIncrement = false;
        } else if (this.inputs[1].floatvalue == 0.0 && !shouldIncrement) {
            shouldIncrement = true;
        }
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.mode = nbttagcompound.getString("mode");
        this.tickAmount = nbttagcompound.getInteger("tickAmount");
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.putString("mode", mode);
        nbttagcompound.putInt("tickAmount", tickAmount);
    }

    public void setMode(String string) {
        if (mode.equals("none")) {
            mode = string;
            switch(string) {
                case "constant":
                    hasSettings = true;
                case "TRUE":
                case "FALSE":
                    this.inputs = new WiringButton[0];
                    this.outputs = new WiringButton[1];
                    this.outputs[0] = new WiringButton(214, 240, "Output", 0);
                    break;
                case "inc":
                case "dec":
                case "mem":
                    this.inputs = new WiringButton[3];
                    this.outputs = new WiringButton[1];
                    this.outputs[0] = new WiringButton(214, 240, "Output", 0);
                    this.inputs[0] = new WiringButton(214, 220, "Source", 0);
                    this.inputs[1] = new WiringButton(214, 200, "Clock", 1);
                    this.inputs[2] = new WiringButton(214, 180, "Reset", 2);
                    break;
                case "incdec":
                    this.inputs = new WiringButton[4];
                    this.outputs = new WiringButton[1];
                    this.outputs[0] = new WiringButton(214, 240, "Output", 0);
                    this.inputs[0] = new WiringButton(214, 220, "Source", 0);
                    this.inputs[1] = new WiringButton(214, 220, "Increment", 1);
                    this.inputs[2] = new WiringButton(214, 200, "Decrement", 2);
                    this.inputs[3] = new WiringButton(214, 180, "Reset", 3);
                    break;
                case "dup":
                    this.inputs = new WiringButton[1];
                    this.outputs = new WiringButton[2];
                    this.outputs[0] = new WiringButton(214, 230, "Output A", 0);
                    this.outputs[1] = new WiringButton(214, 210, "Output B", 1);
                    this.inputs[0] = new WiringButton(214, 230, "Input", 0);
                    break;
                case "Pulse":
                    hasSettings = true;
                case "NOT":
                    this.inputs = new WiringButton[1];
                    this.outputs = new WiringButton[1];
                    this.outputs[0] = new WiringButton(214, 230, "Output", 0);
                    this.inputs[0] = new WiringButton(214, 210, "Input", 0);
                    break;
                case "AND":
                case "OR":
                case "XOR":
                case "NAND":
                case "NOR":
                case "XNOR":
                case "add":
                case "sub":
                case "mult":
                case "div":
                case "==":
                case "!=":
                case ">":
                case ">=":
                case "<":
                case "<=":
                case "<=>":
                    this.inputs = new WiringButton[2];
                    this.outputs = new WiringButton[1];
                    this.outputs[0] = new WiringButton(214, 230, "Output", 0);
                    this.inputs[0] = new WiringButton(214, 210, "Input A", 0);
                    this.inputs[1] = new WiringButton(214, 190, "Input B", 1);
                    break;
            }
            initialized = true;
            worldObj.markBlockNeedsUpdate(x, y, z);
            update();
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return new WiremodPacketSyncIO(this.x, this.y, this.z, inputs, outputs, mode, tickAmount);
    }
}
