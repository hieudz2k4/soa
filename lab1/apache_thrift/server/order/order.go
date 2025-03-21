// Code generated by Thrift Compiler (0.21.0). DO NOT EDIT.

package order

import (
	"bytes"
	"context"
	"errors"
	"fmt"
	"log/slog"
	"time"
	thrift "github.com/apache/thrift/lib/go/thrift"
	"strings"
	"regexp"
)

// (needed to ensure safety because of naive import list construction.)
var _ = bytes.Equal
var _ = context.Background
var _ = errors.New
var _ = fmt.Printf
var _ = slog.Log
var _ = time.Now
var _ = thrift.ZERO
// (needed by validator.)
var _ = strings.Contains
var _ = regexp.MatchString

// Attributes:
//  - TotalPrice
// 
type OrderConfirmation struct {
	TotalPrice *float64 `thrift:"totalPrice,1" db:"totalPrice" json:"totalPrice,omitempty"`
}

func NewOrderConfirmation() *OrderConfirmation {
	return &OrderConfirmation{}
}

var OrderConfirmation_TotalPrice_DEFAULT float64

func (p *OrderConfirmation) GetTotalPrice() float64 {
	if !p.IsSetTotalPrice() {
		return OrderConfirmation_TotalPrice_DEFAULT
	}
	return *p.TotalPrice
}

func (p *OrderConfirmation) IsSetTotalPrice() bool {
	return p.TotalPrice != nil
}

func (p *OrderConfirmation) Read(ctx context.Context, iprot thrift.TProtocol) error {
	if _, err := iprot.ReadStructBegin(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T read error: ", p), err)
	}


	for {
		_, fieldTypeId, fieldId, err := iprot.ReadFieldBegin(ctx)
		if err != nil {
			return thrift.PrependError(fmt.Sprintf("%T field %d read error: ", p, fieldId), err)
		}
		if fieldTypeId == thrift.STOP {
			break
		}
		switch fieldId {
		case 1:
			if fieldTypeId == thrift.DOUBLE {
				if err := p.ReadField1(ctx, iprot); err != nil {
					return err
				}
			} else {
				if err := iprot.Skip(ctx, fieldTypeId); err != nil {
					return err
				}
			}
		default:
			if err := iprot.Skip(ctx, fieldTypeId); err != nil {
				return err
			}
		}
		if err := iprot.ReadFieldEnd(ctx); err != nil {
			return err
		}
	}
	if err := iprot.ReadStructEnd(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T read struct end error: ", p), err)
	}
	return nil
}

func (p *OrderConfirmation) ReadField1(ctx context.Context, iprot thrift.TProtocol) error {
	if v, err := iprot.ReadDouble(ctx); err != nil {
		return thrift.PrependError("error reading field 1: ", err)
	} else {
		p.TotalPrice = &v
	}
	return nil
}

func (p *OrderConfirmation) Write(ctx context.Context, oprot thrift.TProtocol) error {
	if err := oprot.WriteStructBegin(ctx, "OrderConfirmation"); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write struct begin error: ", p), err)
	}
	if p != nil {
		if err := p.writeField1(ctx, oprot); err != nil { return err }
	}
	if err := oprot.WriteFieldStop(ctx); err != nil {
		return thrift.PrependError("write field stop error: ", err)
	}
	if err := oprot.WriteStructEnd(ctx); err != nil {
		return thrift.PrependError("write struct stop error: ", err)
	}
	return nil
}

func (p *OrderConfirmation) writeField1(ctx context.Context, oprot thrift.TProtocol) (err error) {
	if p.IsSetTotalPrice() {
		if err := oprot.WriteFieldBegin(ctx, "totalPrice", thrift.DOUBLE, 1); err != nil {
			return thrift.PrependError(fmt.Sprintf("%T write field begin error 1:totalPrice: ", p), err)
		}
		if err := oprot.WriteDouble(ctx, float64(*p.TotalPrice)); err != nil {
			return thrift.PrependError(fmt.Sprintf("%T.totalPrice (1) field write error: ", p), err)
		}
		if err := oprot.WriteFieldEnd(ctx); err != nil {
			return thrift.PrependError(fmt.Sprintf("%T write field end error 1:totalPrice: ", p), err)
		}
	}
	return err
}

func (p *OrderConfirmation) Equals(other *OrderConfirmation) bool {
	if p == other {
		return true
	} else if p == nil || other == nil {
		return false
	}
	if p.TotalPrice != other.TotalPrice {
		if p.TotalPrice == nil || other.TotalPrice == nil {
			return false
		}
		if (*p.TotalPrice) != (*other.TotalPrice) { return false }
	}
	return true
}

func (p *OrderConfirmation) String() string {
	if p == nil {
		return "<nil>"
	}
	return fmt.Sprintf("OrderConfirmation(%+v)", *p)
}

func (p *OrderConfirmation) LogValue() slog.Value {
	if p == nil {
		return slog.AnyValue(nil)
	}
	v := thrift.SlogTStructWrapper{
		Type: "*order.OrderConfirmation",
		Value: p,
	}
	return slog.AnyValue(v)
}

var _ slog.LogValuer = (*OrderConfirmation)(nil)

func (p *OrderConfirmation) Validate() error {
	return nil
}

type OrderService interface {
	// Parameters:
	//  - ProductId
	//  - Quantity
	// 
	CalculateTotal(ctx context.Context, productId string, quantity int32) (_r *OrderConfirmation, _err error)
}

type OrderServiceClient struct {
	c thrift.TClient
	meta thrift.ResponseMeta
}

func NewOrderServiceClientFactory(t thrift.TTransport, f thrift.TProtocolFactory) *OrderServiceClient {
	return &OrderServiceClient{
		c: thrift.NewTStandardClient(f.GetProtocol(t), f.GetProtocol(t)),
	}
}

func NewOrderServiceClientProtocol(t thrift.TTransport, iprot thrift.TProtocol, oprot thrift.TProtocol) *OrderServiceClient {
	return &OrderServiceClient{
		c: thrift.NewTStandardClient(iprot, oprot),
	}
}

func NewOrderServiceClient(c thrift.TClient) *OrderServiceClient {
	return &OrderServiceClient{
		c: c,
	}
}

func (p *OrderServiceClient) Client_() thrift.TClient {
	return p.c
}

func (p *OrderServiceClient) LastResponseMeta_() thrift.ResponseMeta {
	return p.meta
}

func (p *OrderServiceClient) SetLastResponseMeta_(meta thrift.ResponseMeta) {
	p.meta = meta
}

// Parameters:
//  - ProductId
//  - Quantity
// 
func (p *OrderServiceClient) CalculateTotal(ctx context.Context, productId string, quantity int32) (_r *OrderConfirmation, _err error) {
	var _args0 OrderServiceCalculateTotalArgs
	_args0.ProductId = productId
	_args0.Quantity = quantity
	var _result2 OrderServiceCalculateTotalResult
	var _meta1 thrift.ResponseMeta
	_meta1, _err = p.Client_().Call(ctx, "calculateTotal", &_args0, &_result2)
	p.SetLastResponseMeta_(_meta1)
	if _err != nil {
		return
	}
	if _ret3 := _result2.GetSuccess(); _ret3 != nil {
		return _ret3, nil
	}
	return nil, thrift.NewTApplicationException(thrift.MISSING_RESULT, "calculateTotal failed: unknown result")
}

type OrderServiceProcessor struct {
	processorMap map[string]thrift.TProcessorFunction
	handler OrderService
}

func (p *OrderServiceProcessor) AddToProcessorMap(key string, processor thrift.TProcessorFunction) {
	p.processorMap[key] = processor
}

func (p *OrderServiceProcessor) GetProcessorFunction(key string) (processor thrift.TProcessorFunction, ok bool) {
	processor, ok = p.processorMap[key]
	return processor, ok
}

func (p *OrderServiceProcessor) ProcessorMap() map[string]thrift.TProcessorFunction {
	return p.processorMap
}

func NewOrderServiceProcessor(handler OrderService) *OrderServiceProcessor {

	self4 := &OrderServiceProcessor{handler:handler, processorMap:make(map[string]thrift.TProcessorFunction)}
	self4.processorMap["calculateTotal"] = &orderServiceProcessorCalculateTotal{handler:handler}
	return self4
}

func (p *OrderServiceProcessor) Process(ctx context.Context, iprot, oprot thrift.TProtocol) (success bool, err thrift.TException) {
	name, _, seqId, err2 := iprot.ReadMessageBegin(ctx)
	if err2 != nil { return false, thrift.WrapTException(err2) }
	if processor, ok := p.GetProcessorFunction(name); ok {
		return processor.Process(ctx, seqId, iprot, oprot)
	}
	iprot.Skip(ctx, thrift.STRUCT)
	iprot.ReadMessageEnd(ctx)
	x5 := thrift.NewTApplicationException(thrift.UNKNOWN_METHOD, "Unknown function " + name)
	oprot.WriteMessageBegin(ctx, name, thrift.EXCEPTION, seqId)
	x5.Write(ctx, oprot)
	oprot.WriteMessageEnd(ctx)
	oprot.Flush(ctx)
	return false, x5
}

type orderServiceProcessorCalculateTotal struct {
	handler OrderService
}

func (p *orderServiceProcessorCalculateTotal) Process(ctx context.Context, seqId int32, iprot, oprot thrift.TProtocol) (success bool, err thrift.TException) {
	var _write_err6 error
	args := OrderServiceCalculateTotalArgs{}
	if err2 := args.Read(ctx, iprot); err2 != nil {
		iprot.ReadMessageEnd(ctx)
		x := thrift.NewTApplicationException(thrift.PROTOCOL_ERROR, err2.Error())
		oprot.WriteMessageBegin(ctx, "calculateTotal", thrift.EXCEPTION, seqId)
		x.Write(ctx, oprot)
		oprot.WriteMessageEnd(ctx)
		oprot.Flush(ctx)
		return false, thrift.WrapTException(err2)
	}
	iprot.ReadMessageEnd(ctx)

	tickerCancel := func() {}
	// Start a goroutine to do server side connectivity check.
	if thrift.ServerConnectivityCheckInterval > 0 {
		var cancel context.CancelCauseFunc
		ctx, cancel = context.WithCancelCause(ctx)
		defer cancel(nil)
		var tickerCtx context.Context
		tickerCtx, tickerCancel = context.WithCancel(context.Background())
		defer tickerCancel()
		go func(ctx context.Context, cancel context.CancelCauseFunc) {
			ticker := time.NewTicker(thrift.ServerConnectivityCheckInterval)
			defer ticker.Stop()
			for {
				select {
				case <-ctx.Done():
					return
				case <-ticker.C:
					if !iprot.Transport().IsOpen() {
						cancel(thrift.ErrAbandonRequest)
						return
					}
				}
			}
		}(tickerCtx, cancel)
	}

	result := OrderServiceCalculateTotalResult{}
	if retval, err2 := p.handler.CalculateTotal(ctx, args.ProductId, args.Quantity); err2 != nil {
		tickerCancel()
		err = thrift.WrapTException(err2)
		if errors.Is(err2, thrift.ErrAbandonRequest) {
			return false, thrift.WrapTException(err2)
		}
		if errors.Is(err2, context.Canceled) {
			if err := context.Cause(ctx); errors.Is(err, thrift.ErrAbandonRequest) {
				return false, thrift.WrapTException(err)
			}
		}
		_exc7 := thrift.NewTApplicationException(thrift.INTERNAL_ERROR, "Internal error processing calculateTotal: " + err2.Error())
		if err2 := oprot.WriteMessageBegin(ctx, "calculateTotal", thrift.EXCEPTION, seqId); err2 != nil {
			_write_err6 = thrift.WrapTException(err2)
		}
		if err2 := _exc7.Write(ctx, oprot); _write_err6 == nil && err2 != nil {
			_write_err6 = thrift.WrapTException(err2)
		}
		if err2 := oprot.WriteMessageEnd(ctx); _write_err6 == nil && err2 != nil {
			_write_err6 = thrift.WrapTException(err2)
		}
		if err2 := oprot.Flush(ctx); _write_err6 == nil && err2 != nil {
			_write_err6 = thrift.WrapTException(err2)
		}
		if _write_err6 != nil {
			return false, thrift.WrapTException(_write_err6)
		}
		return true, err
	} else {
		result.Success = retval
	}
	tickerCancel()
	if err2 := oprot.WriteMessageBegin(ctx, "calculateTotal", thrift.REPLY, seqId); err2 != nil {
		_write_err6 = thrift.WrapTException(err2)
	}
	if err2 := result.Write(ctx, oprot); _write_err6 == nil && err2 != nil {
		_write_err6 = thrift.WrapTException(err2)
	}
	if err2 := oprot.WriteMessageEnd(ctx); _write_err6 == nil && err2 != nil {
		_write_err6 = thrift.WrapTException(err2)
	}
	if err2 := oprot.Flush(ctx); _write_err6 == nil && err2 != nil {
		_write_err6 = thrift.WrapTException(err2)
	}
	if _write_err6 != nil {
		return false, thrift.WrapTException(_write_err6)
	}
	return true, err
}


// HELPER FUNCTIONS AND STRUCTURES

// Attributes:
//  - ProductId
//  - Quantity
// 
type OrderServiceCalculateTotalArgs struct {
	ProductId string `thrift:"productId,1" db:"productId" json:"productId"`
	Quantity int32 `thrift:"quantity,2" db:"quantity" json:"quantity"`
}

func NewOrderServiceCalculateTotalArgs() *OrderServiceCalculateTotalArgs {
	return &OrderServiceCalculateTotalArgs{}
}



func (p *OrderServiceCalculateTotalArgs) GetProductId() string {
	return p.ProductId
}



func (p *OrderServiceCalculateTotalArgs) GetQuantity() int32 {
	return p.Quantity
}

func (p *OrderServiceCalculateTotalArgs) Read(ctx context.Context, iprot thrift.TProtocol) error {
	if _, err := iprot.ReadStructBegin(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T read error: ", p), err)
	}


	for {
		_, fieldTypeId, fieldId, err := iprot.ReadFieldBegin(ctx)
		if err != nil {
			return thrift.PrependError(fmt.Sprintf("%T field %d read error: ", p, fieldId), err)
		}
		if fieldTypeId == thrift.STOP {
			break
		}
		switch fieldId {
		case 1:
			if fieldTypeId == thrift.STRING {
				if err := p.ReadField1(ctx, iprot); err != nil {
					return err
				}
			} else {
				if err := iprot.Skip(ctx, fieldTypeId); err != nil {
					return err
				}
			}
		case 2:
			if fieldTypeId == thrift.I32 {
				if err := p.ReadField2(ctx, iprot); err != nil {
					return err
				}
			} else {
				if err := iprot.Skip(ctx, fieldTypeId); err != nil {
					return err
				}
			}
		default:
			if err := iprot.Skip(ctx, fieldTypeId); err != nil {
				return err
			}
		}
		if err := iprot.ReadFieldEnd(ctx); err != nil {
			return err
		}
	}
	if err := iprot.ReadStructEnd(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T read struct end error: ", p), err)
	}
	return nil
}

func (p *OrderServiceCalculateTotalArgs) ReadField1(ctx context.Context, iprot thrift.TProtocol) error {
	if v, err := iprot.ReadString(ctx); err != nil {
		return thrift.PrependError("error reading field 1: ", err)
	} else {
		p.ProductId = v
	}
	return nil
}

func (p *OrderServiceCalculateTotalArgs) ReadField2(ctx context.Context, iprot thrift.TProtocol) error {
	if v, err := iprot.ReadI32(ctx); err != nil {
		return thrift.PrependError("error reading field 2: ", err)
	} else {
		p.Quantity = v
	}
	return nil
}

func (p *OrderServiceCalculateTotalArgs) Write(ctx context.Context, oprot thrift.TProtocol) error {
	if err := oprot.WriteStructBegin(ctx, "calculateTotal_args"); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write struct begin error: ", p), err)
	}
	if p != nil {
		if err := p.writeField1(ctx, oprot); err != nil { return err }
		if err := p.writeField2(ctx, oprot); err != nil { return err }
	}
	if err := oprot.WriteFieldStop(ctx); err != nil {
		return thrift.PrependError("write field stop error: ", err)
	}
	if err := oprot.WriteStructEnd(ctx); err != nil {
		return thrift.PrependError("write struct stop error: ", err)
	}
	return nil
}

func (p *OrderServiceCalculateTotalArgs) writeField1(ctx context.Context, oprot thrift.TProtocol) (err error) {
	if err := oprot.WriteFieldBegin(ctx, "productId", thrift.STRING, 1); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write field begin error 1:productId: ", p), err)
	}
	if err := oprot.WriteString(ctx, string(p.ProductId)); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T.productId (1) field write error: ", p), err)
	}
	if err := oprot.WriteFieldEnd(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write field end error 1:productId: ", p), err)
	}
	return err
}

func (p *OrderServiceCalculateTotalArgs) writeField2(ctx context.Context, oprot thrift.TProtocol) (err error) {
	if err := oprot.WriteFieldBegin(ctx, "quantity", thrift.I32, 2); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write field begin error 2:quantity: ", p), err)
	}
	if err := oprot.WriteI32(ctx, int32(p.Quantity)); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T.quantity (2) field write error: ", p), err)
	}
	if err := oprot.WriteFieldEnd(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write field end error 2:quantity: ", p), err)
	}
	return err
}

func (p *OrderServiceCalculateTotalArgs) String() string {
	if p == nil {
		return "<nil>"
	}
	return fmt.Sprintf("OrderServiceCalculateTotalArgs(%+v)", *p)
}

func (p *OrderServiceCalculateTotalArgs) LogValue() slog.Value {
	if p == nil {
		return slog.AnyValue(nil)
	}
	v := thrift.SlogTStructWrapper{
		Type: "*order.OrderServiceCalculateTotalArgs",
		Value: p,
	}
	return slog.AnyValue(v)
}

var _ slog.LogValuer = (*OrderServiceCalculateTotalArgs)(nil)

// Attributes:
//  - Success
// 
type OrderServiceCalculateTotalResult struct {
	Success *OrderConfirmation `thrift:"success,0" db:"success" json:"success,omitempty"`
}

func NewOrderServiceCalculateTotalResult() *OrderServiceCalculateTotalResult {
	return &OrderServiceCalculateTotalResult{}
}

var OrderServiceCalculateTotalResult_Success_DEFAULT *OrderConfirmation

func (p *OrderServiceCalculateTotalResult) GetSuccess() *OrderConfirmation {
	if !p.IsSetSuccess() {
		return OrderServiceCalculateTotalResult_Success_DEFAULT
	}
	return p.Success
}

func (p *OrderServiceCalculateTotalResult) IsSetSuccess() bool {
	return p.Success != nil
}

func (p *OrderServiceCalculateTotalResult) Read(ctx context.Context, iprot thrift.TProtocol) error {
	if _, err := iprot.ReadStructBegin(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T read error: ", p), err)
	}


	for {
		_, fieldTypeId, fieldId, err := iprot.ReadFieldBegin(ctx)
		if err != nil {
			return thrift.PrependError(fmt.Sprintf("%T field %d read error: ", p, fieldId), err)
		}
		if fieldTypeId == thrift.STOP {
			break
		}
		switch fieldId {
		case 0:
			if fieldTypeId == thrift.STRUCT {
				if err := p.ReadField0(ctx, iprot); err != nil {
					return err
				}
			} else {
				if err := iprot.Skip(ctx, fieldTypeId); err != nil {
					return err
				}
			}
		default:
			if err := iprot.Skip(ctx, fieldTypeId); err != nil {
				return err
			}
		}
		if err := iprot.ReadFieldEnd(ctx); err != nil {
			return err
		}
	}
	if err := iprot.ReadStructEnd(ctx); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T read struct end error: ", p), err)
	}
	return nil
}

func (p *OrderServiceCalculateTotalResult) ReadField0(ctx context.Context, iprot thrift.TProtocol) error {
	p.Success = &OrderConfirmation{}
	if err := p.Success.Read(ctx, iprot); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T error reading struct: ", p.Success), err)
	}
	return nil
}

func (p *OrderServiceCalculateTotalResult) Write(ctx context.Context, oprot thrift.TProtocol) error {
	if err := oprot.WriteStructBegin(ctx, "calculateTotal_result"); err != nil {
		return thrift.PrependError(fmt.Sprintf("%T write struct begin error: ", p), err)
	}
	if p != nil {
		if err := p.writeField0(ctx, oprot); err != nil { return err }
	}
	if err := oprot.WriteFieldStop(ctx); err != nil {
		return thrift.PrependError("write field stop error: ", err)
	}
	if err := oprot.WriteStructEnd(ctx); err != nil {
		return thrift.PrependError("write struct stop error: ", err)
	}
	return nil
}

func (p *OrderServiceCalculateTotalResult) writeField0(ctx context.Context, oprot thrift.TProtocol) (err error) {
	if p.IsSetSuccess() {
		if err := oprot.WriteFieldBegin(ctx, "success", thrift.STRUCT, 0); err != nil {
			return thrift.PrependError(fmt.Sprintf("%T write field begin error 0:success: ", p), err)
		}
		if err := p.Success.Write(ctx, oprot); err != nil {
			return thrift.PrependError(fmt.Sprintf("%T error writing struct: ", p.Success), err)
		}
		if err := oprot.WriteFieldEnd(ctx); err != nil {
			return thrift.PrependError(fmt.Sprintf("%T write field end error 0:success: ", p), err)
		}
	}
	return err
}

func (p *OrderServiceCalculateTotalResult) String() string {
	if p == nil {
		return "<nil>"
	}
	return fmt.Sprintf("OrderServiceCalculateTotalResult(%+v)", *p)
}

func (p *OrderServiceCalculateTotalResult) LogValue() slog.Value {
	if p == nil {
		return slog.AnyValue(nil)
	}
	v := thrift.SlogTStructWrapper{
		Type: "*order.OrderServiceCalculateTotalResult",
		Value: p,
	}
	return slog.AnyValue(v)
}

var _ slog.LogValuer = (*OrderServiceCalculateTotalResult)(nil)


