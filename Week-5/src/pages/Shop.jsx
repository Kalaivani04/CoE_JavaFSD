import { useState } from "react";
import { useCart } from "../context/CartContext";

const products = [
  { id: 1, name: "Face Serum", price: 599, image: "assets/face-serum.png" },
  { id: 2, name: "Moisturizer", price: 399, image: "assets/moisturizer.png" },
  { id: 3, name: "Sunscreen", price: 499, image: "assets/sunscreen.png" },
  { id: 4, name: "Face Pack", price: 349, image: "assets/face-pack.png" },
  { id: 5, name: "Under Eye Cream", price: 299, image: "assets/under-eye-cream.png" },
  { id: 6, name: "Lip Balm", price: 249, image: "assets/lip-balm.png" }
];

const Shop = () => {
  const { dispatch } = useCart();
  const [addedProduct, setAddedProduct] = useState(null);

  const addToCart = (product) => {
    dispatch({ type: "ADD_TO_CART", payload: product });
    setAddedProduct(product.id);

    // Remove "Added" message after 1.5 seconds
    setTimeout(() => setAddedProduct(null), 1500);
  };

  return (
    <div className="w-full min-h-screen bg-[#d1dbcd] p-8">
      <h1 className="text-3xl font-bold text-center mb-6">Shop</h1>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {products.map((product) => (
          <div key={product.id} className="border p-4 rounded-md shadow-md bg-white">
            <img src={product.image} alt={product.name} className="w-full h-48 object-cover rounded-md" />
            <h2 className="text-xl font-semibold mt-4">{product.name}</h2>
            <p className="text-gray-600">₹{product.price}</p>
            <button
              onClick={() => addToCart(product)}
              className="mt-4 px-4 py-2 bg-green-700 text-white rounded-full w-full hover:bg-green-800 transition relative"
            >
              {addedProduct === product.id ? "Added ✓" : "Add to Cart"}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Shop;
