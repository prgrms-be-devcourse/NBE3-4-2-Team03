import OrderForm from '/OrderForm';

const OrderPage = ({ estimateId }) => {
    return (
        <div>
            <h1>주문 페이지</h1>
            <OrderForm estimateId={estimateId} />
        </div>
    );
};

export default OrderPage;

// This gets called on every request
export async function getServerSideProps(context) {
    // Fetch the estimate ID (replace with your actual logic)
    const estimateId = context.query.estimateId || 1;

    return {
        props: { estimateId },
    };
}
