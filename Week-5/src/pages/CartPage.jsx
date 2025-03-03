import { useCart } from "../context/CartContext";

const CartPage = () => {
  const { cart, dispatch } = useCart();

  const removeFromCart = (id) => {
    dispatch({ type: "REMOVE_FROM_CART", payload: id });
  };

  const totalPrice = cart.reduce((sum, item) => sum + item.price, 0);

  return (
    <div className="w-full min-h-screen bg-[#d1dbcd] p-8">
      <h1 className="text-2xl font-bold mb-4">Shopping Cart</h1>
      {cart.length === 0 ? (
        <p className="text-center text-gray-600">Your cart is empty.</p>
      ) : (
        <ul className="space-y-4">
          {cart.map((item) => (
            <li key={item.id} className="flex justify-between bg-white border p-4 rounded-md shadow-md">
              <div>
                <h2 className="text-xl font-semibold">{item.name}</h2>
                <p className="text-gray-600">₹{item.price}</p>
              </div>
              <button
                onClick={() => removeFromCart(item.id)}
                className="bg-red-500 text-white px-4 py-2 rounded-full"
              >
                Remove
              </button>
            </li>
          ))}
        </ul>
      )}
      <h2 className="text-xl font-bold mt-6">Total: ₹{totalPrice.toFixed(2)}</h2>
    </div>
  );
};

export default CartPage;