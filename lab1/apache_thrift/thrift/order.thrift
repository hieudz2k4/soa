namespace java org.app.order
namespace go order

struct OrderConfirmation {
  1: optional double totalPrice       
}

service OrderService {
  OrderConfirmation calculateTotal(1: string productId, 2: i32 quantity)
}

