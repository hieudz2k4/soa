import { Link } from "react-router-dom";
import { Button } from "../components/ui/button";

export default function NotFoundPage() {
  return (
    <div className="relative flex flex-col items-center justify-center min-h-screen text-center px-4 bg-gradient-to-br from-gray-950 via-black to-gray-900 overflow-hidden">
      {/* Hiệu ứng ánh sáng 3D */}
      <div className="absolute inset-0 pointer-events-none">
        <div className="absolute -top-40 left-1/2 w-[500px] h-[500px] bg-blue-500 opacity-30 blur-[150px] transform -translate-x-1/2"></div>
        <div className="absolute bottom-[-100px] right-[-100px] w-[400px] h-[400px] bg-purple-500 opacity-25 blur-[120px]"></div>
      </div>

      {/* Nội dung */}
      <div className="relative bg-gray-900/80 p-10 rounded-2xl shadow-xl border border-gray-800 backdrop-blur-md">
        {/* Tiêu đề gradient */}
        <h1 className="text-5xl font-extrabold mb-4 text-transparent bg-clip-text bg-gradient-to-r from-red-500 to-yellow-500 drop-shadow-lg">
          Paste Not Found
        </h1>

        <p className="text-gray-400 mb-6 text-lg">
          The paste you're looking for doesn't exist or has expired.
        </p>

        {/* Nút bấm hiệu ứng 3D */}
        <Button
          asChild
          className="relative bg-gradient-to-r from-blue-500 to-cyan-500 text-white px-6 py-3 rounded-lg text-lg font-semibold 
          shadow-lg transition-all duration-300 transform hover:scale-110 hover:shadow-2xl active:scale-95"
        >
          <Link to="/">Create a New Paste</Link>
        </Button>
      </div>
    </div>
  );
}
