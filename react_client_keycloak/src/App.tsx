import { BrowserRouter, Routes, Route } from "react-router-dom";
import Authen from "./Authen";
import Callback from "./Callback";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Authen />} />
        <Route path="/callback" element={<Callback />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
