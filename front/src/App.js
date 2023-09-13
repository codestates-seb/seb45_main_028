import "./App.css";
import QuestionList from "./pages/QuestionList";
import "./css/input.css";
import ReviewList from "./pages/ReviewList";
import ProductWrite from "./pages/ProductWrite";
import QuestionWrite from "./pages/QuestionWrite";
import ProductList from "./pages/ProductList";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ProductUpdate from "./pages/ProductUpdate";


function App() {
  return (
    <BrowserRouter>
      <div classname="App">
        <Routes>
          <Route path="/questionlist" element={<QuestionList />} />
          <Route path="/reviewlist" element={<ReviewList />} />
          <Route path="/productwrite" element={<ProductWrite />} />
          <Route path="/questionwrite" element={<QuestionWrite />} />
          <Route path="/productlist" element={<ProductList />} />
          <Route path="/productupdate" element={<ProductUpdate />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
