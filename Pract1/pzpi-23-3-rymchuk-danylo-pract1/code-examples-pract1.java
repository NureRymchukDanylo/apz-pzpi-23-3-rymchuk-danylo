// Product
class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;
    private boolean hasBluetooth;

    public void setCpu(String cpu) { this.cpu = cpu; }
    public void setRam(String ram) { this.ram = ram; }
    public void setStorage(String storage) { this.storage = storage; }
    public void setGpu(String gpu) { this.gpu = gpu; }
    public void setHasBluetooth(boolean hasBluetooth) { this.hasBluetooth = hasBluetooth; }

    @Override
    public String toString() {
        return "Computer [CPU=" + cpu + ", RAM=" + ram + ", Storage=" + storage + 
               ", GPU=" + gpu + ", Bluetooth=" + hasBluetooth + "]";
    }
}

// Builder 
interface ComputerBuilder {
    void buildCPU();
    void buildRAM();
    void buildStorage();
    void buildGPU();
    void buildBluetooth();
    Computer getResult();
}

// ConcreteBuilder 
class GamingComputerBuilder implements ComputerBuilder {
    private Computer computer;

    public GamingComputerBuilder() {
        this.computer = new Computer();
    }

    public void buildCPU() { computer.setCpu("Intel Core i9"); }
    public void buildRAM() { computer.setRam("64GB DDR5"); }
    public void buildStorage() { computer.setStorage("2TB NVMe SSD"); }
    public void buildGPU() { computer.setGpu("NVIDIA RTX 4090"); }
    public void buildBluetooth() { computer.setHasBluetooth(true); }
    
    public Computer getResult() { return computer; }
}

// ConcreteBuilder 
class OfficeComputerBuilder implements ComputerBuilder {
    private Computer computer;

    public OfficeComputerBuilder() {
        this.computer = new Computer();
    }

    public void buildCPU() { computer.setCpu("Intel Core i3"); }
    public void buildRAM() { computer.setRam("8GB DDR4"); }
    public void buildStorage() { computer.setStorage("256GB SATA SSD"); }
    public void buildGPU() { computer.setGpu("Integrated Graphics"); }
    public void buildBluetooth() { computer.setHasBluetooth(false); }
    
    public Computer getResult() { return computer; }
}

// Director 
class ComputerDirector {
    private ComputerBuilder builder;

    public ComputerDirector(ComputerBuilder builder) {
        this.builder = builder;
    }

    public void constructMinimalPC() {
        builder.buildCPU();
        builder.buildRAM();
        builder.buildStorage();
    }

    public void constructFullPC() {
        builder.buildCPU();
        builder.buildRAM();
        builder.buildStorage();
        builder.buildGPU();
        builder.buildBluetooth();
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        GamingComputerBuilder gamingBuilder = new GamingComputerBuilder();
        ComputerDirector director = new ComputerDirector(gamingBuilder);
        
        director.constructFullPC();
        Computer gamingPC = gamingBuilder.getResult();
        System.out.println("Gaming PC: " + gamingPC);

        OfficeComputerBuilder officeBuilder = new OfficeComputerBuilder();
        director = new ComputerDirector(officeBuilder);
        
        director.constructMinimalPC();
        Computer officePC = officeBuilder.getResult();
        System.out.println("Office PC: " + officePC);
    }
}