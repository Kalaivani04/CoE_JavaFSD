import { Link } from "react-router-dom";
import { useState, useEffect } from "react";

const Home = () => {
  const [timeLeft, setTimeLeft] = useState({});
  const [targetDate] = useState(() => {
    const date = new Date();
    date.setHours(date.getHours() + 2);
    return date;
  });

  useEffect(() => {
    const calculateTimeLeft = () => {
      const difference = targetDate - new Date();
      if (difference > 0) {
        setTimeLeft({
          hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
          minutes: Math.floor((difference / (1000 * 60)) % 60),
          seconds: Math.floor((difference / 1000) % 60),
        });
      } else {
        setTimeLeft({ hours: 0, minutes: 0, seconds: 0 });
      }
    };

    calculateTimeLeft();
    const timer = setInterval(calculateTimeLeft, 1000);

    return () => clearInterval(timer);
  }, [targetDate]);

  const products = [
    { id: 1, name: "Face Serum", price: "₹599", img: "assets/face-serum.png", badge: "New Arrival" },
    { id: 2, name: "Moisturizer", price: "₹399", img: "assets/moisturizer.png", badge: "Bestseller" },
    { id: 3, name: "Sunscreen", price: "₹499", img: "assets/sunscreen.png", badge: "" },
    { id: 4, name: "Face Pack", price: "₹349", img: "assets/face-pack.png", badge: "New Arrival" },
    { id: 5, name: "Eye Cream", price: "₹299", img: "assets/under-eye-cream.png", badge: "Bestseller" },
  ];

  return (
    <div className="min-h-screen flex flex-col bg-[#d1dbcd]">
      {/* Countdown Bar */}
      <div className="w-full py-4 text-center text-2xl font-bold bg-opacity-0">
        <span className="text-green-700">Exclusive Deals Ends In:</span>
        <span className="ml-3 text-black">
          {String(timeLeft.hours).padStart(2, "0")}h{" "}
          {String(timeLeft.minutes).padStart(2, "0")}m{" "}
          {String(timeLeft.seconds).padStart(2, "0")}s
        </span>
      </div>

      {/* Hero Section */}
      <section className="w-full min-h-screen flex flex-col-reverse lg:flex-row items-center justify-between px-8 lg:px-16 py-20">
        <div className="text-center lg:text-left lg:w-1/2">
          <h1 className="text-4xl lg:text-5xl font-serif text-white leading-tight">
            UNLOCK YOUR <br /> NATURAL GLOW
          </h1>
          <p className="text-lg text-black mt-4">
            Products that harness the power of 100% natural ingredients.
          </p>
          <Link to="/shop">
            <button className="mt-6 px-6 py-3 bg-green-700 text-white rounded-full hover:bg-green-800 transition">
              Shop now
            </button>
          </Link>
        </div>
        <div className="lg:w-1/2 flex justify-center">
          <img 
            src="assets/hero-image.png" 
            alt="Natural Beauty" 
            className="w-[90%] lg:w-[80%] max-w-[700px] object-cover rounded-full shadow-lg"
          />
        </div>
      </section>

      {/* New Arrivals Section */}
      <section className="w-full px-4 py-16 lg:px-0">
      <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">New Arrivals</h2>
      <div className="grid grid-cols-5 gap-4 w-full justify-start">
        {products.map((product) => (
      <div key={product.id} className="relative bg-white rounded-xl shadow-lg p-4">
        {product.badge && (
          <span className="absolute top-2 left-2 bg-green-700 text-white text-xs px-3 py-1 rounded-full">
            {product.badge}
          </span>
        )}
        <img src={product.img} alt={product.name} className="w-full h-40 object-cover rounded-lg" />
        <div className="mt-4 text-center">
          <h3 className="text-lg font-semibold text-gray-900">{product.name}</h3>
          <p className="text-gray-600">{product.price}</p>
          <button className="mt-2 px-4 py-2 bg-green-700 text-white rounded-full hover:bg-green-800 transition">
            Add to Cart
          </button>
        </div>
      </div>
    ))}
  </div>
</section>
    </div>
  );
};

export default Home;
