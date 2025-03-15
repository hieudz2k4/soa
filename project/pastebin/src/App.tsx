import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import ViewPastePage from "./pages/ViewPastePage";
import NotFoundPage from "./pages/NotFoundPage";
import { ThemeProvider } from "./components/theme-provider";

function App() {
  return (
    <ThemeProvider defaultTheme="light" storageKey="paste-theme">
      <Router>
        {/* Hiệu ứng nền gradient hiện đại */}
        <div className="relative min-h-screen bg-gradient-to-br from-gray-950 via-black to-gray-900 text-white overflow-hidden">
          {/* Hiệu ứng ánh sáng mờ */}
          <div className="absolute inset-0 pointer-events-none">
            <div className="absolute -top-40 left-1/2 w-[500px] h-[500px] bg-blue-500 opacity-20 blur-[150px] transform -translate-x-1/2"></div>
            <div className="absolute bottom-[-100px] right-[-100px] w-[400px] h-[400px] bg-purple-500 opacity-15 blur-[120px]"></div>
          </div>

          {/* Nội dung */}
          <div className="relative z-10">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/:pasteId" element={<ViewPastePage />} />
              <Route path="/not-found" element={<NotFoundPage />} />
            </Routes>
          </div>
        </div>
      </Router>
    </ThemeProvider>
  );
}

export default App;
