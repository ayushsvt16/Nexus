import React, { useState } from 'react';

const Login = ({ onLogin, onClose }) => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: ''
  });
  const [isSignUp, setIsSignUp] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const generateInitials = (name) => {
    return name
      .split(' ')
      .map(word => word.charAt(0).toUpperCase())
      .join('')
      .substring(0, 2);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
  if (isSignUp) {
      // Sign up logic
      const userData = {
        name: formData.name,
        email: formData.email,
        initials: generateInitials(formData.name)
      };
      
      // Store user data in localStorage (for frontend-only persistence)
      localStorage.setItem('user', JSON.stringify(userData));
      onLogin(userData);
    } else {
      // Login logic - only name and password required for demo
      if (formData.name) {
        const userData = {
          name: formData.name,
          initials: generateInitials(formData.name)
        };
        localStorage.setItem('user', JSON.stringify(userData));
        onLogin(userData);
      }
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-6 w-96 max-w-md">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-bold">
            {isSignUp ? 'Sign Up' : 'Login'}
          </h2>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-gray-700 text-xl"
          >
            ×
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1">Full Name</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              className="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
              placeholder="Enter your full name"
              required
            />
          </div>

          {isSignUp && (
            <div>
              <label className="block text-sm font-medium mb-1">Email</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
                placeholder="Enter your email"
                required
              />
            </div>
          )}

          <div>
            <label className="block text-sm font-medium mb-1">Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500"
              placeholder="Enter your password"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full bg-orange-600 text-white py-2 rounded-md hover:bg-orange-700 transition"
          >
            {isSignUp ? 'Sign Up' : 'Login'}
          </button>
        </form>

        <div className="mt-4 text-center">
          <button
            onClick={() => setIsSignUp(!isSignUp)}
            className="text-orange-600 hover:underline"
          >
            {isSignUp 
              ? 'Already have an account? Login' 
              : "Don't have an account? Sign Up"
            }
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
