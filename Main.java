import java.util.ArrayList;
import java.util.List;

// 1. Watcher Interface (Common contract for all observers)
interface OrderWatcher {
    void update(String status);
}

// 2. Concrete Watcher: Customer Phone (SMS)
class CustomerPhone implements OrderWatcher {
    private String customerName;

    public CustomerPhone(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void update(String status) {
        System.out.println("SMS to " + customerName + ": your order is now " + status);
    }
}

// 3. Concrete Watcher: Kitchen Screen
class KitchenScreen implements OrderWatcher {
    @Override
    public void update(String status) {
        System.out.println("Kitchen screen: " + status);
    }
}

// 4. Subject Class: Order (Does not mention phone or kitchen by name)
class Order {
    private String status;
    private List<OrderWatcher> watchers = new ArrayList<>();

    public void addWatcher(OrderWatcher watcher) {
        watchers.add(watcher);
    }

    public void removeWatcher(OrderWatcher watcher) {
        watchers.remove(watcher);
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("\nOrder is now: " + status);
        notifyWatchers();
    }

    private void notifyWatchers() {
        for (OrderWatcher watcher : watchers) {
            watcher.update(status);
        }
    }
}

// 5. Main Class to demonstrate the flow
public class Main {
    public static void main(String[] args) {
        // Create the order
        Order order = new Order();

        // Create watchers
        CustomerPhone rahimPhone = new CustomerPhone("Rahim");
        KitchenScreen kitchenScreen = new KitchenScreen();

        // Rule 3 & 4: Add both watchers initially
        order.addWatcher(rahimPhone);
        order.addWatcher(kitchenScreen);

        // Status change: Cooking
        order.setStatus("Cooking");

        // Status change: Out for delivery
        order.setStatus("Out for delivery");

        // Rule 3: Kitchen is not notified when delivered, so remove kitchen watcher
        order.removeWatcher(kitchenScreen);

        // Status change: Delivered
        order.setStatus("Delivered");
    }
}
