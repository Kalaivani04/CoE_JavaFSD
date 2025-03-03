import { createContext, useReducer, useContext, useEffect } from "react";

// Load cart from local storage (if available)
const loadCartFromStorage = () => {
  const savedCart = localStorage.getItem("cart");
  return savedCart ? JSON.parse(savedCart) : [];
};

// Initial state for the cart
const initialState = {
  cart: loadCartFromStorage(),
};

// Reducer function to handle cart actions
const cartReducer = (state, action) => {
  switch (action.type) {
    case "ADD_TO_CART": {
      const updatedCart = [...state.cart, action.payload]; // Allow duplicates
      localStorage.setItem("cart", JSON.stringify(updatedCart)); // Save to local storage
      return { cart: updatedCart };
    }

    case "REMOVE_FROM_CART": {
      const index = state.cart.findIndex(item => item.id === action.payload);
      if (index !== -1) {
        const updatedCart = [...state.cart];
        updatedCart.splice(index, 1); // Remove only the first matching instance
        localStorage.setItem("cart", JSON.stringify(updatedCart)); // Save to local storage
        return { cart: updatedCart };
      }
      return state;
    }

    default:
      return state;
  }
};

// Create Cart Context
const CartContext = createContext();

// Provider component
export const CartProvider = ({ children }) => {
  const [state, dispatch] = useReducer(cartReducer, initialState);

  // Sync state with local storage when the cart changes
  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(state.cart));
  }, [state.cart]);

  return (
    <CartContext.Provider value={{ cart: state.cart, dispatch }}>
      {children}
    </CartContext.Provider>
  );
};

// Custom hook to use Cart Context
export const useCart = () => {
  return useContext(CartContext);
};
