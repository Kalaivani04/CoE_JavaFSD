import { Link } from "react-router-dom";
import { useCart } from "../context/CartContext";

const Navbar = () => {
  const { cart } = useCart();

  return (
    <nav className="bg-white shadow-md py-4 px-8 flex justify-between items-center">
      <Link to="/" className="flex items-center space-x-2">
        <img src="assets/logo.png" alt="Logo" className="h-10 w-auto" />
        <span className="text-3xl font-cursive text-green-900">Cozy</span>
      </Link>
      <div className="flex space-x-6">
        <Link to="/" className="text-gray-700 hover:text-green-700">Home</Link>
        <Link to="/shop" className="text-gray-700 hover:text-green-700">Shop</Link>
        <Link to="/about" className="text-gray-700 hover:text-green-700">About us</Link>
        <Link to="/contact" className="text-gray-700 hover:text-green-700">Contact</Link>
      </div>
      <div className="flex space-x-4">
        <Link to="/cart" className="text-gray-700 hover:text-green-700 flex items-center space-x-2 relative">
          <span className="font-medium">My Cart</span>
          <div className="relative">
            <img src="assets/cart.png" alt="Cart" className="w-6 h-6" />
            {cart.length > 0 && (
              <span className="absolute -top-2 -right-2 bg-red-600 text-white text-xs font-bold px-2 py-1 rounded-full">
                {cart.length}
              </span>
            )}
          </div>
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
