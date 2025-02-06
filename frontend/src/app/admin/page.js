'use client';
import React, { useState, useEffect } from 'react';

const ItemList = () => {
  const [categories, setCategories] = useState([]);  // 카테고리 목록 상태
  const [items, setItems] = useState([]);  // 선택된 카테고리의 부품 목록 상태
  const [selectedCategory, setSelectedCategory] = useState(null);  // 선택된 카테고리 상태

  // 카테고리 불러오기
  useEffect(() => {
    fetch('http://localhost:8080/api/admin/categories')  // 카테고리 목록 가져오기
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {  // 데이터가 배열인지 확인
          setCategories(data);
          console.log('카테고리 데이터:', data);  // 데이터 확인용 로그
        } else {
          console.error('잘못된 데이터 형식:', data);
        }
      })
      .catch((error) => console.error('카테고리 로딩 실패:', error));
  }, []);

  // 카테고리 클릭 시 해당 부품 불러오기
  const handleCategoryClick = (categoryId) => {
    setSelectedCategory(categoryId);

    fetch(`http://localhost:8080/api/admin/items?categoryId=${categoryId}`)  // 해당 카테고리 부품 목록 불러오기
      .then((response) => response.json())
      .then((data) => setItems(data))
      .catch((error) => console.error('부품 로딩 실패:', error));
  };

  return (
    <div>
      <h1>카테고리와 부품 목록</h1>

      {/* 카테고리 이름들이 가로로 나열됨 */}
      <div>
        <h2>카테고리</h2>
        <div style={{ display: 'flex', gap: '10px', overflowX: 'scroll', paddingBottom: '20px' }}>
          {categories.length > 0 ? (
            categories.map((category) => (
              <div
                key={category.id}
                onClick={() => handleCategoryClick(category.id)}
                style={{
                  padding: '10px 20px',
                  backgroundColor: selectedCategory === category.id ? '#4CAF50' : '#ddd',
                  color: selectedCategory === category.id ? '#fff' : '#000',
                  borderRadius: '5px',
                  cursor: 'pointer',
                  transition: 'background-color 0.3s',
                }}
              >
                {category.category}  {/* 카테고리 이름 */}
              </div>
            ))
          ) : (
            <p>카테고리가 없습니다.</p>
          )}
        </div>
      </div>

      {/* 선택된 카테고리 부품 목록 */}
      {selectedCategory && (
        <div>
          <h2>선택한 카테고리: {categories.find(c => c.id === selectedCategory)?.category}</h2>
          <h3>부품 목록</h3>
          <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px' }}>
            {items.length > 0 ? (
              items.map((item) => (
                <div key={item.id} style={{ textAlign: 'center', width: '150px' }}>
                  <img
                    src={`/images/${item.filename}`}  // 이미지 경로에 맞게 수정
                    alt={item.name}
                    width={100}
                    style={{ marginBottom: '10px' }}
                  />
                  <p>{item.name}</p>
                </div>
              ))
            ) : (
              <p>선택된 카테고리에 해당하는 부품이 없습니다.</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default ItemList;