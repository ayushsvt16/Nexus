import { useState } from "react";
import DropdownIcon from "../assets/dropdown.svg";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className=" font-inter flex items-center justify-between px-16 py-3 border-b-2 bg-white shadow">
      {/* Logo */}
      <div className="flex items-center space-x-2">
        <img src="/src/assets/logo.svg" alt="IIITBH Logo" className="h-6 w-6" />
        <span className="text-xl font-bold text-orange-600">IIITBH</span>
        <span className="text-xl font-bold">Nexus</span>
      </div>

      {/* Desktop Menu */}
      <div className="hidden md:flex items-center space-x-6">
        {/* Exam Vault */}
        <div className="flex items-center space-x-1 hover:text-orange-500 cursor-pointer">
          <a className="flex items-center space-x-1">
            <span>Home</span>            
          </a>
        </div>
        <div className="relative group">
          <button className="hover:text-orange-500 flex items-center space-x-2">
            <span>Exam Vault</span>
            <img
              src={DropdownIcon}
              alt="Dropdown"
              className="w-3 h-3 transition-transform duration-200 group-hover:rotate-180 relative top-[1px]"
            />
          </button>          
        </div>

        {/* Feedback */}
        <div className="relative group">
          <button className="font-inter hover:text-orange-500 flex items-center space-x-2 ">
            <span>Feedback</span>
            <img
              src={DropdownIcon}
              alt="Dropdown"
              className="w-3 h-3 transition-transform duration-200 group-hover:rotate-180 relative top-[1px]"
            />
          </button>          
        </div>

        {/* Sign Up */}
        <button className="bg-black text-white px-4 py-2 rounded-md hover:opacity-80">
          Log In
        </button>
      </div>

      {/* Mobile Menu Button */}
      <div className="md:hidden">
        <button onClick={() => setIsOpen(!isOpen)} className="text-2xl">
          ☰
        </button>
      </div>

      {/* Mobile Menu */}
      {isOpen && (
        <div className="absolute top-16 left-0 w-full bg-white border-b shadow-md md:hidden z-50">
          <div className="flex flex-col space-y-2 p-4">
            <a className="hover:text-orange-500">
              Home
            </a>
            <details>
              <summary className="cursor-pointer hover:text-orange-500">
                Exam Vault
              </summary>             
            </details>
            <details>
              <summary className="cursor-pointer hover:text-orange-500">
                Feedback
              </summary>                
            </details>
            <button className="bg-black text-white px-4 py-2 rounded-md hover:opacity-80">
              Log In
            </button>
          </div>
        </div>
      )}
      </nav>
    );
  };

export default Navbar;
